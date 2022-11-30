package com.codehub.finmanager.util

import com.codehub.finmanager.R
import com.codehub.finmanager.model.Budget
import com.codehub.finmanager.model.ChartItem
import com.codehub.finmanager.model.Transaction

object Constants {

    val BASE_URL = "http://154.159.237.82:3000"
    val BASE_URL_EMULATOR = "http://10.0.2.2:3000"
    val budgets = listOf(
        Budget("Food", 2000.00, 7000.00, R.drawable.ic_food),
        Budget("Medicine", 500.00, 4000.00, R.drawable.ic_medicine),
        Budget("Travel", 3000.00, 3500.00, R.drawable.ic_travel),
        Budget("Clothing", 1000.00, 2000.00, R.drawable.ic_cloth),
        Budget("Wifi", 200.00, 1000.00, R.drawable.ic_wifi),
    )

    val transactions = listOf(
        Transaction("Food", "Daily bevareges, grocerries, vegetables...", true,"16 Nov, 2022", R.drawable.ic_food),
        Transaction("Medicine", "Daily bevareges, grocerries, vegetables...", false,"16 Nov, 2022", R.drawable.ic_medicine),
        Transaction("Travel", "Daily bevareges, grocerries, vegetables...", false,"16 Nov, 2022", R.drawable.ic_travel),
        Transaction("Fees", "Daily bevareges, grocerries, vegetables...", true,"16 Nov, 2022", R.drawable.ic_fees),
        Transaction("Wifi", "Daily bevareges, grocerries, vegetables...", false,"16 Nov, 2022", R.drawable.ic_wifi),
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