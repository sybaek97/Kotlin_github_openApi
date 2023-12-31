package com.crepass.kotlin_github_openapi.network

import com.crepass.kotlin_github_openapi.model.Repo
import com.crepass.kotlin_github_openapi.model.UserDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {



    @GET("/users/{username}/repos")
    fun listRepos(@Path("username") username:String,@Query("page") page:Int): Call<List<Repo>>

    @GET("search/users")
    fun searchUsers(@Query("q")query: String):Call<UserDto>
}