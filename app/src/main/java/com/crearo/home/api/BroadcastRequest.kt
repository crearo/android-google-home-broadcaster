package com.crearo.home.api

import com.crearo.home.Config
import com.squareup.moshi.Json

data class BroadcastRequest(
    @field:Json(name = "command") val command: String,
    @field:Json(name = "broadcast") val broadcast: Boolean = true,
    @field:Json(name = "user") val user: String = Config.DEFAULT_USER
)