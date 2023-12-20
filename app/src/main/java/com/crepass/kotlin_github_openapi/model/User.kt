package com.crepass.kotlin_github_openapi.model

import com.google.gson.annotations.SerializedName

data class User (
    @SerializedName("id")
    val id:Int,

    @SerializedName("login")
    val username:String,

)