package com.example.currencyconverter.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.currencyconverter.data.local.entity.CurrencyRateEntity
import com.example.currencyconverter.data.remote.dto.CurrencyDto

@Dao
interface CurrencyRateDao {

    @Upsert
    suspend fun upsertAll(currencyRates : List<CurrencyRateEntity>)

    @Query("SELECT * FROM currencyrateentity")
    suspend fun getALlCurrencyRates():List<CurrencyRateEntity>
}