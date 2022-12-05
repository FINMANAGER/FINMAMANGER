package com.codehub.finmanager.model

data class Transaction(
    var amount: Double = 0.0,
    var title: String = "",
    var category: String = "",
    @field:JvmField
    var isIncome: Boolean? = null,
    var date: String = "",
    var description: String = "",

)
