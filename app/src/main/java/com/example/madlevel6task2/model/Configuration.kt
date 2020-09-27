package com.example.madlevel6task2.model

import com.google.gson.annotations.SerializedName

data class Configuration (
    @SerializedName("images") val images : ImageConfiguration,
    @SerializedName("change_keys") val changeKeys : List<String>
)