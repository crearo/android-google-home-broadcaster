package com.crearo.home

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object GoogleHomeApiClient {

    val getClient: GoogleHomeService
        get() {
            val retrofit = Retrofit.Builder()
                .baseUrl(Config.GOOGLE_HOME_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
            return retrofit.create(GoogleHomeService::class.java)
        }
}