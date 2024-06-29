package com.lindahasanah.prediksistunting.response

import com.google.gson.annotations.SerializedName

data class StuntingResponse(

    @field:SerializedName("result")
    val result: String? = null,

    @field:SerializedName("input")
    val input: Input? = null,

    @field:SerializedName("status")
    val status: String? = null
)