package com.example.flo.NetWork

import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("userIdx") val userIdx: Int,
    @SerializedName("jwt") val jwt: String
)
