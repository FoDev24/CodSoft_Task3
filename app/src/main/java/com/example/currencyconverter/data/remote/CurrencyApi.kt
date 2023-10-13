package com.example.currencyconverter.data.remote

import com.example.currencyconverter.data.remote.dto.CurrencyDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {
    @GET("latest")
    suspend fun getLatestRates(
        @Query("apikey") apikey : String = API_KEY
    ):CurrencyDto

    companion object{
        const val API_KEY = "fca_live_9q5e5WiRvvDfHH8zEX7BhjEBQqOOoDgDqYNYgUfO"
        const val BASE_URL = "https://api.freecurrencyapi.com/v1/"
    }
}