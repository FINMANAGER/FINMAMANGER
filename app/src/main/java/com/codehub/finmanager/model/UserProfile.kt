package com.codehub.finmanager.model

data class UserProfile(
    var username: String = "",
    var uid: String = "",
    var fullName: String ="",
    var budget: Double = 0.0,
    var spending: Double = 0.0,
)
