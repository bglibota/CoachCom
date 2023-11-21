package com.example.coachcom.network

import com.example.coachcom.models.LoginData
import com.example.coachcom.models.ResponseData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {

    @POST("API_V2/users/login")
    fun loginUser(@Body dataLogin: LoginData): Call<ResponseData>

}