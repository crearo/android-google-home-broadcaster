package com.crearo.home

import com.squareup.moshi.Json

data class BroadcastRequest(
    @field:Json(name = "command") val command: String,
    @field:Json(name = "broadcast") val broadcast: Boolean,
    @field:Json(name = "user") val user: String
)