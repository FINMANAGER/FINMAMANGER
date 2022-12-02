package com.codehub.finmanager.ui

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources.getColorStateList
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.codehub.finmanager.MainActivity
import com.codehub.finmanager.R
import com.codehub.finmanager.adapters.PictureAdapter
import com.codehub.finmanager.databinding.FragmentAddTransactionBinding
import com.codehub.finmanager.model.TransactionModel
import com.codehub.finmanager.util.CategoryOptions
import com.codehub.finmanager.util.Constants
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

class AddTransaction : Fragment() {

    private var date: Long = 0
    private lateinit var binding: FragmentAddTransactionBinding
    private lateinit var pictureAdapter: PictureAdapter
   //private lateinit var etCategory: AutoCompleteTextView
    private var type: Int = 1
    private lateinit var dbRef: DatabaseReference //initialize database
    private var isSubmitted: Boolean = false
    private var amount: Double = 0.0
    private var invertedDate: Long = 0
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (requireActivity() as MainActivity).apply {
            handleBottomBarVisibility(beVisible = true)
            handleBottomBarActions()
        }
        binding = FragmentAddTransactionBinding.inflate(inflater)
        pictureAdapter = PictureAdapter()

        //---back button---
        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
        //--------

        //---category menu dropdown---
        val listExpense = CategoryOptions.expenseCategory() //getting the arrayList data from CategoryOptions file
        val expenseAdapter = activity?.let { ArrayAdapter(it, android.R.layout.simple_spinner_dropdown_item, listExpense) }
        binding.category.setAdapter(expenseAdapter)

        //---date picker---
        val sdf = java.text.SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
        val currentDate = sdf.parse(sdf.format(System.currentTimeMillis())) //take current date
        date = currentDate!!.time //initialized date value to current date as the default value
        binding.etdate.setOnClickListener {
            clickDatePicker()
        }
        //----

        //--radio button option choosing---
        binding.typeRadioGroup.setOnCheckedChangeListener { _, checkedID ->
            binding.category.text.clear() //clear the category autocompletetextview when the type changes
            if (checkedID == R.id.rbExpense) {
                type = 1 //expense
                setBackgroundColor()
                binding.category.setAdapter(expenseAdapter) //if expense type selected, the set list expense array in category menu
            }
            if (checkedID == R.id.rbIncome){
                type = 2 //income
                setBackgroundColor()

                //if expense type selected, the set list income array in category menu :
                val listIncome = CategoryOptions.incomeCategory()
                val incomeAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, listIncome)
                binding.category.setAdapter(incomeAdapter)
            }
        }
        //-----

        // --Initialize Firebase Auth and firebase database--
//        val user = Firebase.auth.currentUser
//        val uid = user?.uid
//        if (uid != null) {
//            dbRef = FirebaseDatabase.getInstance().getReference(uid) //initialize database with uid as the parent
//        }
        //auth = Firebase.auth
        //--Saving the data button---
        binding.saveButton.setOnClickListener {
            if (!isSubmitted){
                saveTransactionData()
            }else{
                Snackbar.make(binding.root.findViewById(android.R.id.content), "You have saved the transaction data", Snackbar.LENGTH_LONG).show()
            }

        }

        return binding.root
    }

    private fun saveTransactionData() {
        dbRef = Firebase.database.reference //initialize database with uid as the parent
        //getting values from form input user:
        val title = binding.title.text.toString()
        val category = binding.category.text.toString()
        val amountEt = binding.amount.text.toString()
        val note = binding.note.text.toString()

        if(amountEt.isEmpty()){
           // binding.amount.text.error = "Please enter Amount"
            binding.amount.requestFocus()
        }else if(title.isEmpty()) {
           // etTitle.error = "Please enter Title"
            binding.title.requestFocus()
        }else if(category.isEmpty()){
           // etCategory.error = "Please enter Category"
            binding.category.requestFocus()
        }else{
            amount = binding.amount.text.toString().toDouble() //convert to double type

            val transactionID = dbRef.push().key!!
            invertedDate = date * -1 //convert millis value to negative, so it can be sort as descending order
            val transaction = TransactionModel(transactionID, type, title, category, amount, date, note, invertedDate) //object of data class

            dbRef.child(transactionID).setValue(transaction)
                .addOnCompleteListener {
                    Toast.makeText(context, "Data Inserted Successfully", Toast.LENGTH_LONG).show()
                    findNavController().popBackStack()
                   // finish()
                }.addOnFailureListener { err ->
                    Toast.makeText(context, "Error ${err.message}", Toast.LENGTH_LONG).show()
                }

            isSubmitted = true
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pictureAdapter.submitList(Constants.pitures)
        binding.apply {
//            saveButton.setOnClickListener {
//                findNavController().popBackStack()
//            }
//            rvPictures.apply {
//                adapter = pictureAdapter
//            }
        }
    }

    private fun clickDatePicker() {
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(requireContext(),
            { _, selectedYear, selectedMonth, selectedDayOfMonth ->

                val selectedDate = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
                binding.etdate.text = null
                binding.etdate.hint = selectedDate

                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                val theDate = sdf.parse(selectedDate)
                date = theDate!!.time //convert date to millisecond

            },
            year,
            month,
            day
        )
        dpd.show()
    }

    private fun setBackgroundColor() {
        if (type == 1){
            binding.rbExpense.setBackgroundResource(R.drawable.radio_selected_expense)
            binding.rbIncome.setBackgroundResource(R.drawable.radio_not_selected)
            binding.toolbarLinear.setBackgroundResource(R.color.purple_500)
            binding.saveButton.backgroundTintList = getColorStateList(requireContext(),R.color.purple_500)
            activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.purple_500)

        }else{
            binding.rbIncome.setBackgroundResource(R.drawable.radio_selected_income)
            binding.rbExpense.setBackgroundResource(R.drawable.radio_not_selected)
            binding.toolbarLinear.setBackgroundResource(R.color.purple_200)
            binding.saveButton.backgroundTintList = getColorStateList(requireContext(),R.color.purple_200)
            activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.purple_200)
        }
    }
}