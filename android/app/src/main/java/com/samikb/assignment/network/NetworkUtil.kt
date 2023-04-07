package com.samikb.assignment.network

import com.samikb.assignment.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkUtil {
    private val logging: HttpLoggingInterceptor = HttpLoggingInterceptor()
    private var client: OkHttpClient
    private var retrofit: Retrofit
    var movieInfoService: MovieInfoService
    init {
        logging.level = HttpLoggingInterceptor.Level.BASIC;
        client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build();
        retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        movieInfoService = retrofit.create(MovieInfoService::class.java)
    }


}