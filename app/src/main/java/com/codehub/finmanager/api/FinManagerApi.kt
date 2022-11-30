package com.codehub.finmanager.api

import com.codehub.finmanager.model.Budget
import com.codehub.finmanager.model.Transaction
import com.codehub.finmanager.model.User
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface FinManagerApi {
    @GET("/budgets")
    suspend fun getBudgets():Response<Flow<List<Budget>>>

    @GET("/budget")
    suspend fun getBudget(): Response<Flow<Budget>>

    @GET("/spending")
    suspend fun getTransactions():Response<Flow<List<Transaction>>>

    @GET("/transaction")
    suspend fun getTransaction(): Response<Flow<Transaction>>

    @POST("/budget")
    suspend fun addBudget(@Body budget: Budget)

    @POST("/")
    suspend fun addTransaction(transaction: Transaction)

    @POST("/auth/sign-in")
    suspend fun loginUser(@Body user: User):Response<User>

    @POST("/auth/sign-up")
    suspend fun signUpUser(@Body user: User):Response<User>

    @POST("/auth/sign-out")
    suspend fun signOutUser(@Body user: User)
}