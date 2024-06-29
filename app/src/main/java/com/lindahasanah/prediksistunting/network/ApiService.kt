package com.lindahasanah.prediksistunting.network

import com.lindahasanah.prediksistunting.response.StuntingResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("predict/stunting")
    fun getPredict(
        @Query("umur") umur: Int,
        @Query("gender") gender: String,
        @Query("tinggi") tinggi: Float
    ): Call<StuntingResponse>
}