package com.codehub.finmanager.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codehub.finmanager.model.Budget
import com.codehub.finmanager.model.Transaction
import com.codehub.finmanager.model.User
import com.codehub.finmanager.model.UserProfile
import com.codehub.finmanager.reposiory.FinManagerRepo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class FinManagerViewModel : ViewModel() {
    private val repository = FinManagerRepo()
    var auth:FirebaseAuth? = null
    val currentUser = MutableStateFlow(User("","",""))
    val error = MutableLiveData(false)
    val success = MutableLiveData(false)
    val loading = MutableLiveData(false)
    val networkError = MutableLiveData(false)
    val errorMessage = MutableLiveData("")
    val errorGetSpending = MutableLiveData("")
    var uid:String? = null
    private val firStoreRef = Firebase.firestore
    var isUserProfileCreated = MutableStateFlow(false)
    val profileCreationError = MutableStateFlow("")
    val getBudgetsError = MutableStateFlow("")
    val getTransactionsError = MutableStateFlow("")
    val totalIncome = MutableStateFlow(0.0)
    val totalExpenses = MutableStateFlow(0.0)

    var transactions = MutableStateFlow<List<Transaction?>>(emptyList())
    var budgetList= MutableStateFlow<List<Budget?>>(emptyList())

    fun setCurrentUser(user: User){
        currentUser.value = user
    }

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
                    errorMessage.value = ""

                } else {
                    error.value = true
                    loading.value = false
                    networkError.value = false
                    success.value = false
                    errorMessage.value = response.message()
                }

            } catch (e: Exception) {
                loading.value = false
                error.value = false
                success.value = false
                networkError.value = true
                errorMessage.value = e.message

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
                    errorMessage.value = ""

                } else {
                    error.value = true
                    loading.value = false
                    networkError.value = false
                    success.value = false
                    errorMessage.value = response.message()
                }

            } catch (e: Exception) {
                loading.value = false
                error.value = false
                success.value = false
                networkError.value = true
                errorMessage.value = e.message

            }

        }
    }

    fun getTransactions() {
        uid?.let {
            firStoreRef.collection("transaction")
                .document(it).collection("transaction")
                .get()
                .addOnSuccessListener { result ->
                    val transactionList = result.documents.map {
                        it.toObject(Transaction::class.java)
                    }
                    transactions.value = transactionList
                    totalExpenses.value = transactionList.filter { !it!!.isIncome }.sumOf { it!!.amount }

                }
                .addOnFailureListener { exception ->
                    getTransactionsError.value = exception.message?:""
                }
        }
    }

    fun createUserProfile(userProfile: UserProfile) {
        val profile = hashMapOf(
            "username" to userProfile.username,
            "user_id" to userProfile.uid,
            "budget" to userProfile.budget,
            "spending" to userProfile.spending,
        )
        uid?.let {
            firStoreRef.collection("profile").document(it).set(profile)
                .addOnCompleteListener { profileTask ->
                if (profileTask.isSuccessful){
                    isUserProfileCreated.value = true
                }else{
                    isUserProfileCreated.value = false
                    profileCreationError.value = profileTask.exception?.message!!
                }
            }
        }
    }

    private fun getBudgets() {
        if (auth !=null) {
            firStoreRef.collection("budget")
                .document(uid ?: FirebaseAuth.getInstance().uid!!).collection("categories")
                .get()
                .addOnSuccessListener { result ->
                    val budgets = result.documents.map {
                        it.toObject(Budget::class.java)
                    }
                    budgetList.value = budgets
                    totalIncome.value = budgets.sumOf { it?.budget ?: 0.0 }
                    Log.d("ViewModel", "getBudgets: $budgets")

                }
                .addOnFailureListener { exception ->
                    getBudgetsError.value = exception.message ?: ""
                }
        }
        budgetList.value = emptyList()
    }

    fun getUserProfile() {
        uid?.let {
            firStoreRef.collection("profile")
                .document(it).get()
                .addOnSuccessListener { profileResult ->
                    if (profileResult.exists()){
                        val currentProfile = profileResult.toObject(UserProfile::class.java)
                    }
                }
                .addOnFailureListener {

                }
        }
    }

    init {
        //getSpending()
        getBudgets()
    }

}