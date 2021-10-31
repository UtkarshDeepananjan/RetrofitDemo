package com.example.globofly.network

import com.example.globofly.models.Destination
import retrofit2.Call
import retrofit2.http.*

interface DestinationService {
    @GET("destination")
    fun getDestinationList(@Query("country") country: String?): Call<List<Destination>>

    @GET("destination/{id}")
    fun getDestination(@Path("id") id: Int): Call<Destination>

    @POST("destination")
    fun addDestination(@Body newDestination: Destination): Call<Destination>

    @FormUrlEncoded
    @PUT("destination/{id}")
    fun updateDestination(
        @Path("id") id: Int,
        @Field("city") city: String,
        @Field("description") description: String,
        @Field("country") country: String
    ): Call<Destination>

    @DELETE("destination/{id}")
    fun deleteDestination(@Path("id") id: Int): Call<Unit>
}