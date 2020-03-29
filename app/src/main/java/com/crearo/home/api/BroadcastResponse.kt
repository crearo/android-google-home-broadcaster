package com.crearo.home.api

import com.squareup.moshi.Json

data class BroadcastResponse(
    @field:Json(name = "response") val response: String,
    @field:Json(name = "audio") val audio: String,
    @field:Json(name = "success") val success: Boolean
)