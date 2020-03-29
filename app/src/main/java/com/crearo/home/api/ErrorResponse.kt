package com.crearo.home.api

import com.squareup.moshi.Json

data class ErrorResponse(@field:Json(name = "success") val boolean: Boolean)