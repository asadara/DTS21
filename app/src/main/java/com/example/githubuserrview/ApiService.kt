package com.example.githubuserrview

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

//interface retrofit
interface ApiService {
    @GET("search/users?")
    fun getSearchResult(@Query("q") login: String): Call<SearchResponse>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String?): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getUserFollowers(@Path("username") username: String?): Call<ArrayList<UserFollowerResponse>>

    @GET("users/{username}/following")
    fun getUserFollowing(@Path("username") username: String?): Call<ArrayList<UserFollowingResponse>>
}