package com.crearo.home

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface GoogleHomeService {
    @POST("/assistant")
    suspend fun broadcast(@Body broadcastRequest: BroadcastRequest): BroadcastResponse
}