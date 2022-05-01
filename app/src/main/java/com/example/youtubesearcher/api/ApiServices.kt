package com.example.youtubesearcher.api

import com.example.youtubesearcher.models.YtubeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {

    @GET("search")
    fun getVideo(
        @Query("part") part: String,
        @Query("q") q: String,
        @Query("pageToken") pageToken: String?
    ) : Call<YtubeResponse>

}