package com.example.bankapplication.di

import android.content.Context
import com.example.bankapplication.repo.PaymentRepositoryImpl
import com.example.hankapplication.network.PaymentApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Hilt module providing payment API and repository dependencies as singletons.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    @Singleton
    fun providePaymentApi(): PaymentApiService {
        return Retrofit.Builder()
            .baseUrl(PaymentApiService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PaymentApiService::class.java)
    }

    @Provides
    @Singleton
    fun providePaymentRepository(api: PaymentApiService, context: Context): PaymentRepositoryImpl {
        return PaymentRepositoryImpl(api, context)
    }
}