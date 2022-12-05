package com.codehub.finmanager.model

import java.time.temporal.TemporalAmount

data class Transaction(
    var title:String = "",
    var description:String = "",
    var isIncome:Boolean = false,
    var date:String = "",
    var amount: Double =0.0
)
