package com.codehub.finmanager.util

import com.codehub.finmanager.R
import com.codehub.finmanager.model.Budget
import com.codehub.finmanager.model.ChartItem
import com.codehub.finmanager.model.Transaction

object Constants {

    val BASE_URL = "https://finmanager-backend.fly.dev"
    val budgets = listOf(
        Budget("Food", 2000.00, 7000.00, "02/12/2022"),
        Budget("Medicine", 500.00, 4000.00,"02/12/2022"),
        Budget("Travel", 3000.00, 3500.00,"02/12/2022"),
        Budget("Clothing", 1000.00, 2000.00,"02/12/2022"),
        Budget("Wifi", 200.00, 1000.00,"02/12/2022"),
    )

    val transactions = listOf(
        Transaction("Food", "Daily bevareges, grocerries, vegetables...", true,"16 Nov, 2022", 200.0),
        Transaction("Medicine", "Daily bevareges, grocerries, vegetables...", false,"16 Nov, 2022", 25.0),
        Transaction("Travel", "Daily bevareges, grocerries, vegetables...", false,"16 Nov, 2022", 300.0),
        Transaction("Fees", "Daily bevareges, grocerries, vegetables...", true,"16 Nov, 2022", 50.0),
        Transaction("Wifi", "Daily bevareges, grocerries, vegetables...", false,"16 Nov, 2022", 25.0),
    )

    val chartItems = listOf(
        ChartItem(R.color.teal_700, "Food", 1000.00),
        ChartItem(R.color.purple_200, "Medicine", 20.00),
        ChartItem(R.color.teal_200, "Travel", 400.00),
        ChartItem(R.color.purple_700, "Wifi", 150.00),
    )

    val pitures = listOf(R.drawable.ic_food,
        R.drawable.ic_medicine, R.drawable.ic_travel, R.drawable.ic_cloth, R.drawable.ic_wifi )
}