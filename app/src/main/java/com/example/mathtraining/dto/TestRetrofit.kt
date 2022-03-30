package com.example.mathtraining.dto

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface TestRetrofit {

    @GET("/send")
    suspend fun get(): TestDto


    companion object{
        operator fun invoke(): TestRetrofit{
            return Retrofit.Builder()
                .baseUrl("http://192.168.100.4:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TestRetrofit::class.java)
        }
    }
}