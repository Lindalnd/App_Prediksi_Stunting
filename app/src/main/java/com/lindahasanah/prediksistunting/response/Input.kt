package com.lindahasanah.prediksistunting.response

import com.google.gson.annotations.SerializedName

data class Input(

    @field:SerializedName("gender")
    val gender: String? = null,

    @field:SerializedName("umur (bulan)")
    val umur: Int? = null,

    @field:SerializedName("tinggi")
    val tinggi: Any? = null
)