package com.crearo.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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