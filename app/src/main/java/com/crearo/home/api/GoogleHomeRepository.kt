package com.crearo.home.api

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface GoogleHomeRepository {
    suspend fun broadcast(broadcastRequest: BroadcastRequest): ResultWrapper<BroadcastResponse>
}

class GoogleHomeRepositoryImpl(
    private val service: GoogleHomeService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : GoogleHomeRepository {
    override suspend fun broadcast(broadcastRequest: BroadcastRequest): ResultWrapper<BroadcastResponse> {
        return safeApiCall(dispatcher) { service.broadcast(broadcastRequest) }
    }
}