package com.codehub.finmanager.ui

import android.graphics.Color
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codehub.finmanager.R
import com.codehub.finmanager.model.*
import com.codehub.finmanager.reposiory.FinManagerRepo
import com.codehub.finmanager.util.CategoryOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FinManagerViewModel : ViewModel() {
    private val repository = FinManagerRepo()
    var auth: FirebaseAuth? = null
    val currentUser = MutableStateFlow(User("", "", ""))
    val error = MutableLiveData(false)
    val success = MutableLiveData(false)
    val loading = MutableLiveData(false)
    val networkError = MutableLiveData(false)
    val errorMessage = MutableLiveData("")
    val errorGetSpending = MutableLiveData("")
    var uid: MutableStateFlow<String?> = MutableStateFlow("")
    private val firStoreRef = Firebase.firestore
    var isUserProfileCreated = MutableStateFlow(false)
    val profileCreationError = MutableStateFlow("")
    val currentUserProfile = MutableStateFlow(UserProfile())
    val getBudgetsError = MutableStateFlow("")
    val getTransactionsError = MutableStateFlow("")
    val getProfileError = MutableStateFlow("")
    val totalIncome = MutableStateFlow(0.0)
    val totalExpenses = MutableStateFlow(0.0)
    val totalBalance = MutableStateFlow(0.0)
    val incomePercent = MutableStateFlow(0.0)
    val expensePercent = MutableStateFlow(0.0)

    var transactions = MutableStateFlow<List<Transaction?>>(emptyList())
    var budgetList = MutableStateFlow<List<Budget?>>(emptyList())

    fun setCurrentUser(user: User) {
        currentUser.value = user
    }

    fun setCurrentUserUid(currentUid: String) {
        uid.value = currentUid
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
        viewModelScope.launch {
            uid.collect { currentUserId ->
                if (currentUserId != null) {
                    firStoreRef.collection("transaction")
                        .document(currentUserId).collection("transactions")
                        .get()
                        .addOnSuccessListener { result ->
                            val transactionList = result.documents.map {
                                it.toObject(Transaction::class.java)
                            }
                            transactions.value = transactionList

                            val expensesSum = transactionList.filter { it!!.isIncome==false }.sumOf { it!!.amount }
                            val incomeSum = transactionList.filter { it!!.isIncome == true }.sumOf { it!!.amount }
                            Log.d("Viewmodel", "expense: $expensesSum")
                            Log.d("Viewmodel", "Income: $incomeSum")
                            Log.d("Viewmodel", "List: $transactionList")
                            totalExpenses.value = expensesSum
                            totalIncome.value = incomeSum
                            totalBalance.value = incomeSum - expensesSum
                            incomePercent.value = ((incomeSum - expensesSum)/incomeSum)*100
                            expensePercent.value = (expensesSum/incomeSum)*100
                        }
                        .addOnFailureListener { exception ->
                            getTransactionsError.value = exception.message ?: ""
                        }
                }
            }
        }

    }

    fun createUserProfile(userProfile: UserProfile) {
        val profile = hashMapOf(
            "fullname" to userProfile.fullName,
            "username" to userProfile.username,
            "user_id" to userProfile.uid,
            "budget" to userProfile.budget,
            "spending" to userProfile.spending,
        )
        viewModelScope.launch {
            uid.collectLatest { userId ->
                if (userId != null) {
                    firStoreRef.collection("profile").document(userId).set(profile)
                        .addOnCompleteListener { profileTask ->
                            if (profileTask.isSuccessful) {
                                isUserProfileCreated.value = true
                            } else {
                                isUserProfileCreated.value = false
                                profileCreationError.value = profileTask.exception?.message!!
                            }
                        }
                }
            }
        }

    }

    fun updateProfile(userProfile: UserProfile) {
        viewModelScope.launch {
            uid.collectLatest { userId ->
                if (userId != null) {
                    firStoreRef.collection("profile").document(userId)
                        .update(
                            "fullname", userProfile.fullName,
                            "username", userProfile.username
                        )
                        .addOnCompleteListener { profileTask ->
                            if (profileTask.isSuccessful) {
                                isUserProfileCreated.value = true
                            } else {
                                isUserProfileCreated.value = false
                                profileCreationError.value = profileTask.exception?.message!!
                            }
                        }
                }
            }
        }
    }

    fun getBudgets() {
        viewModelScope.launch {
            uid.collect { userId ->
                if (userId != null) {
                    firStoreRef.collection("budget")
                        .document(userId).collection("categories")
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
            }
        }


        budgetList.value = emptyList()
        Log.d("ViewModel", "uid: $uid ")
    }

    fun getUserProfile() {
        viewModelScope.launch {
            uid.collect { currentUserId ->
                if (currentUserId != null) {
                    firStoreRef.collection("profile")
                        .document(currentUserId).get()
                        .addOnSuccessListener { profileResult ->
                            if (profileResult.exists()) {
                                val currentProfile = profileResult.toObject(UserProfile::class.java)
                                Log.d("Profile", "getUserProfile: $currentProfile")
                                if (currentProfile != null) {
                                    currentUserProfile.value = currentProfile
                                    Log.d("Profile", "getUserProfile: $currentProfile")
                                    getProfileError.value = ""
                                }
                            }
                        }
                        .addOnFailureListener {
                            getProfileError.value = it.message.toString()
                        }
                }
            }
        }

    }

    fun getCategoryTransactions(transactions: List<Transaction?>): List<ChartItem> {
        return transactions.groupBy { it!!.category }.map {
            ChartItem(
                amount = it.value
                    .filter { transaction ->  transaction?.isIncome ==false }
                    .sumOf {trans -> trans!!.amount },
                name = it.key,
                color = when(it.key){
                    "Food/Beverage" -> Color.YELLOW
                    "Bills/Fees" -> Color.RED
                    "Travel/Transportation" ->Color.BLUE
                    "Wifi" -> Color.MAGENTA
                    "Medicine" -> Color.GREEN
                    else -> R.color.purple_700
                }
            )
        }

    }


    init {
        //getSpending()
    }

}