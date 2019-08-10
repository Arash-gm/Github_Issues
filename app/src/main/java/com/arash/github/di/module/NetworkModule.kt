package com.arash.github.di.module

import com.arash.github.data.api.ApiCall
import com.arash.github.util.Globals
import com.github.leonardoxh.livedatacalladapter.LiveDataCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjectionModule
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by Arash Golmohammadi on 8/1/2019.
 */

@Module(includes = [AndroidInjectionModule::class])
class NetworkModule{

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {

        return Retrofit.Builder()
            .baseUrl(Globals.API_END_POINT)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): ApiCall {
        return retrofit.create<ApiCall>(ApiCall::class.java!!)
    }

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)
        return httpClient.build()
    }

}