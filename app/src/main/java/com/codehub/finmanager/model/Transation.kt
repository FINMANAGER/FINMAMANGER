package com.codehub.finmanager.model

data class Transaction(
    val title:String,
    val description:String,
    val isIncome:Boolean = false,
    val date:String,
    val imageUrl:Int
)
