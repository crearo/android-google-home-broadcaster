package com.crearo.home.api

import com.crearo.home.api.BroadcastRequest
import com.crearo.home.api.BroadcastResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface GoogleHomeService {
    @POST("/assistant")
    suspend fun broadcast(@Body broadcastRequest: BroadcastRequest): BroadcastResponse
}