package com.codehub.finmanager.reposiory

import com.codehub.finmanager.api.FinManagerApi
import com.codehub.finmanager.api.RetrofitInstance
import com.codehub.finmanager.model.Budget
import com.codehub.finmanager.model.Transaction
import com.codehub.finmanager.model.User

class FinManagerRepo {
    private val finManagerApi: FinManagerApi = RetrofitInstance.retrofit.create(FinManagerApi::class.java)

    suspend fun loginUser(user: User) = finManagerApi.loginUser(user)
    suspend fun sigUpUser(user: User) = finManagerApi.signUpUser(user)


    suspend fun getBudgets() = finManagerApi.getBudgets()
    suspend fun getTransactions() = finManagerApi.getTransactions()
    suspend fun getTransaction() = finManagerApi.getTransaction()
    suspend fun addBudget(budget: Budget) = finManagerApi.addBudget(budget = budget)
    suspend fun addTransaction(transaction: Transaction) = finManagerApi.addTransaction(transaction = transaction)
}