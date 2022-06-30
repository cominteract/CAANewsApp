package com.ainsigne.data.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.serialization.ExperimentalSerializationApi

/**
 * lenient gson builder for conversion
 */
@ExperimentalSerializationApi
fun json(): Gson = GsonBuilder().apply {
    setLenient()
}.create()
