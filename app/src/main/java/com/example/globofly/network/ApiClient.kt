package com.example.globofly.network

import android.os.Build
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

object ApiClient {
    private const val BASE_URL = "http://10.0.2.2:9000/"

    //logging logger
    private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    //create a custom interceptor to apply headers application wide
    val headerInterceptor = Interceptor {
        var request = it.request()
        request = request.newBuilder()
            .addHeader("x-device-type", Build.DEVICE)
            .addHeader("Accept-Language", Locale.getDefault().language)
            .build()
        return@Interceptor it.proceed(request)
    }

    //create okhttp client
    private val okHttpClient =
        OkHttpClient.Builder()
            .callTimeout(5, TimeUnit.SECONDS)// for time out
            .addInterceptor(headerInterceptor)// added before logging interceptor
            .addInterceptor(logger)

    //create retrofit builder
    private val builder = Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient.build())

    //create retrofit instance
    private val retrofit = builder.build()

    fun <T> buildService(serviceType: Class<T>): T {
        return retrofit.create(serviceType)
    }

}