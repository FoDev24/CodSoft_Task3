package com.example.currencyconverter.presentation.main_screen


import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.currencyconverter.R
import com.example.currencyconverter.presentation.main_screen.common.NumberKey

@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun MainScreen(
    state: MainScreenState,
    onEvent: (MainScreenEvent) -> Unit
) {

    val keys = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", ".", "0", "C")

    val context = LocalContext.current

    LaunchedEffect(key1 = state.error) {
        if (state.error != null) {
            Toast.makeText(context, state.error, Toast.LENGTH_LONG)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Text(
            text = "Currency Converter",
            modifier = Modifier.fillMaxWidth(),
            fontFamily = FontFamily.Cursive,
            fontSize = 40.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )

        Box(contentAlignment = Alignment.CenterStart) {
            Column {
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 4.dp),
                        horizontalAlignment = Alignment.End
                    ) {
                        CurrencyRow(
                            modifier = Modifier.fillMaxWidth(),
                            currencyCode = state.fromCurrencyCode,
                            currencyName = state.currencyRates[state.fromCurrencyCode]?.name ?: "",
                            onDropDownIconClicked = {}
                        )
                        Text(
                            text = state.fromCurrencyValue,
                            fontSize = 40.sp,
                            modifier = Modifier.clickable(
                                interactionSource = MutableInteractionSource(),
                                indication = null,
                                onClick = {
                                    onEvent(MainScreenEvent.FromCurrencySelect)
                                }
                            ) ,
                            color = if(state.selection == SelectionStates.FROM){
                                MaterialTheme.colorScheme.primary
                            }else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 4.dp),
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(
                            text = state.toCurrencyValue,
                            fontSize = 40.sp,
                            modifier = Modifier.clickable (
                                interactionSource = MutableInteractionSource(),
                                indication = null,
                                onClick = {
                                    onEvent(MainScreenEvent.ToCurrencySelect)
                                }
                            ) ,
                            color = if(state.selection == SelectionStates.TO){
                                MaterialTheme.colorScheme.primary
                            }else MaterialTheme.colorScheme.onSurface
                        )

                        CurrencyRow(
                            modifier = Modifier.fillMaxWidth(),
                            currencyCode = state.toCurrencyCode,
                            currencyName = state.currencyRates[state.toCurrencyCode]?.name ?: "",
                            onDropDownIconClicked = {}
                        )

                    }
                }
            }
            Box(
                modifier = Modifier
                    .padding(start = 40.dp)
                    .clip(CircleShape)
                    .clickable { onEvent(MainScreenEvent.SwapIconClicked) }
                    .background(color = MaterialTheme.colorScheme.background)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_swap),
                    contentDescription = "Currency Swap",
                    modifier = Modifier
                        .padding(8.dp)
                        .size(25.dp),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        LazyVerticalGrid(
            modifier = Modifier.padding(horizontal = 35.dp),
            columns = GridCells.Fixed(3)
        ) {

            items(keys) { key ->
                NumberKey(
                    modifier = Modifier.aspectRatio(1f),
                    backgroundColor = if (key == "C") MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.surfaceVariant,
                    key = key,
                    onClick = { onEvent(MainScreenEvent.NumberButtonItemClicked(key)) }
                )

            }

        }

    }


}

@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun CurrencyRow(
    modifier: Modifier = Modifier,
    currencyCode: String,
    currencyName: String,
    onDropDownIconClicked: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = currencyCode, fontSize = 14.sp, fontWeight = FontWeight.Bold)
        IconButton(onClick = onDropDownIconClicked) {
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Open Bottom Sheet"
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(text = currencyName, fontSize = 14.sp, fontWeight = FontWeight.Bold)
    }
}