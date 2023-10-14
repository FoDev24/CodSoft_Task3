package com.example.currencyconverter.data.repository

import com.example.currencyconverter.data.local.CurrencyRateDao
import com.example.currencyconverter.data.local.entity.toCurrencyRate
import com.example.currencyconverter.data.local.entity.toCurrencyRateEntity
import com.example.currencyconverter.data.remote.CurrencyApi
import com.example.currencyconverter.data.remote.dto.toCurrencyRates
import com.example.currencyconverter.domain.model.CurrencyRate
import com.example.currencyconverter.domain.model.Resource
import com.example.currencyconverter.domain.repository.CurrencyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class CurrencyRepositoryImp(
    private val api : CurrencyApi ,
    private val dao : CurrencyRateDao
):CurrencyRepository {
    override fun getCurrencyRatesList(): Flow<Resource<List<CurrencyRate>>> = flow{
        val localCurrencyRates = getLocalCurrencyRates()
        emit(Resource.Success(localCurrencyRates))

        try {
            val newRates = getRemoteCurrencyRates()
            updateLocalCurrencyRates(newRates)
            emit(Resource.Success(newRates))
        } catch (e:IOException){
            emit(Resource.Error(message = "Couldn't reach server,check your internet connection",
                data = localCurrencyRates))
        }
        catch (e:Exception){
            emit(Resource.Error(e.message.toString()))
        }
    }

    private suspend fun getLocalCurrencyRates() : List<CurrencyRate>{
        return dao.getALlCurrencyRates().map { it.toCurrencyRate() }
    }

    private suspend fun getRemoteCurrencyRates(): List<CurrencyRate>{
        val response = api.getLatestRates()
        return response.toCurrencyRates()
    }

    private suspend fun updateLocalCurrencyRates(currencyRates: List<CurrencyRate>){
        dao.upsertAll(currencyRates.map { it.toCurrencyRateEntity() })
    }



}