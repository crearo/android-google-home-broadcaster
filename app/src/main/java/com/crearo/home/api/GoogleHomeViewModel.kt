package com.crearo.home.api

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.crearo.home.api.BroadcastRequest
import com.crearo.home.api.BroadcastResponse
import com.crearo.home.api.GoogleHomeApiClient
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class GoogleHomeViewModel : ViewModel() {
    val client = GoogleHomeApiClient.client

    private val parentJob = Job()
    private val coroutineContext: CoroutineContext get() = parentJob + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)

    val responseLiveData = MutableLiveData<BroadcastResponse>()

    fun broadcast(broadcastRequest: BroadcastRequest) {
        scope.launch(Dispatchers.IO) {
            responseLiveData.postValue(client.broadcast(broadcastRequest))
        }
    }

    fun cancelRequests() = coroutineContext.cancel()
}