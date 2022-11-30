package com.codehub.finmanager.model

import com.google.gson.annotations.SerializedName

data class User(
    /*@SerializedName("id")
    var id:Int = -1,*/
    @SerializedName("username")
    var email:String,
    @SerializedName("password")
    var password:String
)
