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
        Transaction(
            200.0,
            "Groceries",
            "Food/Beverage",
            true,
            "16 Nov, 2022",
            "Daily bevareges, grocerries, vegetables...",
            ),
        Transaction(
            category = "Medicine",
            description = "Daily bevareges, grocerries, vegetables...",
            isIncome = false,
           date =  "16 Nov, 2022",
           amount =  25.0,
        ),
        Transaction(
            title = "Tour",
            category = "Travel",
           description =  "Daily bevareges, grocerries, vegetables...",
          isIncome =   false,
         date =    "16 Nov, 2022",
         amount =    300.0,
        ),
        Transaction(
            title = "Sales",
            category = "Salary",
            description = "Daily bevareges, grocerries, vegetables...",
            isIncome = true,
           date =  "16 Nov, 2022",
            amount = 5000.0,
        ),
        Transaction(
            title = "Surfing",
            category = "Wifi",
            description = "Daily bevareges, grocerries, vegetables...",
          isIncome =   false,
           date =  "16/11/2022",
           amount =  25.0,
        ),
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