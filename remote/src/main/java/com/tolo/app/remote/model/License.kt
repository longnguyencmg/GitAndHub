package com.tolo.app.remote.model

import com.google.gson.annotations.SerializedName

class License(
    @SerializedName("key") val key: String,
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
)