package com.example.currencyconverter.di

import android.app.Application
import androidx.room.Room
import com.example.currencyconverter.data.local.CurrencyRateDB
import com.example.currencyconverter.data.local.CurrencyRateDao
import com.example.currencyconverter.data.remote.CurrencyApi
import com.example.currencyconverter.data.repository.CurrencyRepositoryImp
import com.example.currencyconverter.domain.repository.CurrencyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideCurrencyApi(): CurrencyApi {
        val retrofit = Retrofit
            .Builder()
            .baseUrl(CurrencyApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(CurrencyApi::class.java)
    }

    @Singleton
    @Provides
    fun provideLocalDatabase(application: Application): CurrencyRateDB {
        return Room
            .databaseBuilder(
                application,
                CurrencyRateDB::class.java,
                "currency_db"
            )
            .build()
    }

    @Singleton
    @Provides
    fun provideRepository(
        api: CurrencyApi,
        db: CurrencyRateDB
    ): CurrencyRepository {
        return CurrencyRepositoryImp(
            api = api,
            dao = db.currencyRateDao,
        )
    }

}