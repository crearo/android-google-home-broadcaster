package com.crearo.home

object Config {

    const val GOOGLE_HOME_URL: String = "http://192.168.178.44:3000/"

    // TODO(crearo) move this out to a database where users select what they want to do.
    val PACKAGES_OF_INTEREST = arrayOf("com.google.android.calendar")
}