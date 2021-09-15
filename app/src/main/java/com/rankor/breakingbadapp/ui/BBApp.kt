package com.rankor.breakingbadapp.ui

import android.app.Application
import com.rankor.breakingbadapp.data.BBApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BBApp : Application() {

    lateinit var bbApi: BBApi

    override fun onCreate() {
        super.onCreate()

        setupRetrofit()
    }

    // setup retrofit logging and api for calls
    private fun setupRetrofit() {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.breakingbadapi.com/api/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        bbApi = retrofit.create(BBApi::class.java)
    }
}