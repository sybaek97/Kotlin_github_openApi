package com.crepass.kotlin_github_openapi

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL="https://api.github.com/"
    private val okHttpClient=OkHttpClient.Builder()
        .addInterceptor{
            val request=   it.request().newBuilder()
                .addHeader("Authorization"," Bearer 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")
                .build()
            it.proceed(request)
        }
        .build()
    val retrofit:Retrofit= Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()



}