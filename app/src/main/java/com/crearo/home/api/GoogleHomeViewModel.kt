package com.crearo.home.api

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

class GoogleHomeViewModel : ViewModel() {

    private val repository: GoogleHomeRepository =
        GoogleHomeRepositoryImpl(GoogleHomeApiClient.client)
    private val parentJob = Job()
    private val coroutineContext: CoroutineContext get() = parentJob + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)

    val responseLiveData = MutableLiveData<BroadcastResponse>()

    fun broadcast(broadcastRequest: BroadcastRequest) {
        scope.launch(Dispatchers.IO) {
            when (val response = repository.broadcast(broadcastRequest)) {
                is ResultWrapper.Success -> responseLiveData.postValue(response.value)
                else -> {
                    Timber.e("Error in broadcasting $response")
                }
            }
        }
    }

    fun cancelRequests() = coroutineContext.cancel()
}