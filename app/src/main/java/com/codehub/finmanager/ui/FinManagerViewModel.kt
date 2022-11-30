package com.codehub.finmanager.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codehub.finmanager.model.Transaction
import com.codehub.finmanager.model.User
import com.codehub.finmanager.reposiory.FinManagerRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class FinManagerViewModel : ViewModel() {
    private val repository = FinManagerRepo()


    val error = MutableLiveData(false)
    val success = MutableLiveData(false)
    val loading = MutableLiveData(false)
    val networkError = MutableLiveData(false)

    var transactions = MutableStateFlow<List<Transaction>>(emptyList())

    fun signUpUser(user: User) {
        viewModelScope.launch {
            loading.value = true
            try {
                val response = repository.sigUpUser(user)
                if (response.isSuccessful) {
                    loading.value = false
                    error.value = false
                    networkError.value = false
                    success.value = true

                } else {
                    error.value = true
                    loading.value = false
                    networkError.value = false
                    success.value = false
                }

            } catch (e: Exception) {
                loading.value = false
                error.value = false
                success.value = false
                networkError.value = true

            }

        }
    }


    fun signInUser(user: User) {
        viewModelScope.launch {
            loading.value = true
            try {
                val response = repository.loginUser(user)
                if (response.isSuccessful) {
                    loading.value = false
                    error.value = false
                    networkError.value = false
                    success.value = true

                } else {
                    error.value = true
                    loading.value = false
                    networkError.value = false
                    success.value = false
                }

            } catch (e: Exception) {
                loading.value = false
                error.value = false
                success.value = false
                networkError.value = true

            }

        }
    }

    fun getSpending() {
        viewModelScope.launch {
            val response = repository.getTransactions()
            if (response.isSuccessful) {
                response.body()?.collect { transactionsList ->
                    transactions.value = transactionsList
                }
            } else {

            }
        }
    }

    init {
        getSpending()
    }

}