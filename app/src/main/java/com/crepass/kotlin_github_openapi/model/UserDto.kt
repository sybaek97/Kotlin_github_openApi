package com.crepass.kotlin_github_openapi.model

import com.google.gson.annotations.SerializedName

data class UserDto(

    @SerializedName("total_count")
    val totalCount: Int,

    @SerializedName("items")
    val items:List<User>

    )