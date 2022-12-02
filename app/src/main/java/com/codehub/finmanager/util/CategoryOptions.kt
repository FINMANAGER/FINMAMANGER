package com.codehub.finmanager.util

object CategoryOptions {

    fun expenseCategory(): ArrayList<String> {
        val listExpense = ArrayList<String>()
        listExpense.add("Food/Beverage")
        listExpense.add("Medicine")
        listExpense.add("Travel/Transportation")
        listExpense.add("Bills/Fees")
        listExpense.add("Wifi")

        return listExpense
    }

    fun incomeCategory(): ArrayList<String> {
        val listIncome = ArrayList<String>()
        listIncome.add("Salary")
        listIncome.add("Award")
        listIncome.add("Gift")
        listIncome.add("Investment Return")
        listIncome.add("Other Income")

        return listIncome
    }
}