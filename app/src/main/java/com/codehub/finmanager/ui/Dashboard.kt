package com.codehub.finmanager.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.recyclerview.widget.LinearLayoutManager
import com.codehub.finmanager.MainActivity
import com.codehub.finmanager.TransactionDetails
import com.codehub.finmanager.adapters.BudgetAdapter
import com.codehub.finmanager.adapters.TransactionAdapter
import com.codehub.finmanager.databinding.FragmentDashboardBinding
import com.codehub.finmanager.model.TransactionModel
import com.codehub.finmanager.util.Constants
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList

class Dashboard : Fragment() {
    private lateinit var binding: FragmentDashboardBinding
    private lateinit var budgetAdapter: BudgetAdapter
    private lateinit var typeOption: Spinner
    private lateinit var timeSpanOption: Spinner
    var dateStart: Long = 0
    var dateEnd: Long = 0
    private lateinit var dbRef: DatabaseReference

    private var selectedType: String = "All Type"//default is all type
    private var selectedTimeSpan: String = "All Time"
    private lateinit var transactionList: ArrayList<TransactionModel>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity() as MainActivity).apply {
            handleBottomBarVisibility(true)
            handleBottomBarActions()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        budgetAdapter = BudgetAdapter()
        //transactionAdapter = TransactionAdapter()
        budgetAdapter.submitList(Constants.budgets)
//        transactionAdapter.submitList(Constants.transactions)

        //visibilityOptions()

        //--Recycler View transaction items--
        binding.rvTransactionHistory.layoutManager = LinearLayoutManager(this.activity)
        binding.rvTransactionHistory.setHasFixedSize(true)

        transactionList = arrayListOf<TransactionModel>()



        getTransactionData()

//        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh)
//        swipeRefreshLayout.setOnRefreshListener { //call getTransaction() back to refresh the recyclerview
//            getTransactionData()
//            swipeRefreshLayout.isRefreshing = false
//        }
        //----
        binding.apply {
            rvBudget.apply {
                adapter = budgetAdapter
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            }
            rvTransactionHistory.apply {
//                adapter = transactionAdapter
//                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
//                isNestedScrollingEnabled = false
            }
        }
    }

    private fun getTransactionData() {
        //binding.shimmerFrameLayout.startShimmerAnimation()
        binding.shimmerFrameLayout.visibility = View.VISIBLE
        binding.visibilityNoData.visibility = View.GONE
        binding.rvTransactionHistory.visibility = View.GONE //hide the recycler view
        binding.tvNoData.visibility = View.GONE
        binding.noDataImage.visibility = View.GONE
        binding.tvNoDataTitle.visibility = View.GONE

//        val uid = user?.uid //get user id from database
//        if (uid != null) {
//            dbRef = FirebaseDatabase.getInstance().getReference(uid)
//        }
        dbRef = Firebase.database.reference
        val query: Query = dbRef.orderByChild("invertedDate") //sorting date descending
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                transactionList.clear()
                if (snapshot.exists()){
                    when (selectedType) {
                        "All Type" -> { //all option selected
                            for (transactionSnap in snapshot.children){
                                val transactionData = transactionSnap.getValue(TransactionModel::class.java) //reference data class
                                if (selectedTimeSpan == "All Time"){
                                    transactionList.add(transactionData!!)
                                }else{
                                    if (transactionData!!.date!! > dateStart-86400000 &&
                                        transactionData.date!!<= dateEnd){
                                        transactionList.add(transactionData)
                                    }
                                }
                            }
                        }
                        "Expense" -> { //expense option selected
                            for (transactionSnap in snapshot.children){
                                val transactionData = transactionSnap.getValue(TransactionModel::class.java) //reference data class
                                if (transactionData!!.type == 1){ //expense type
                                    if (selectedTimeSpan == "All Time"){
                                        transactionList.add(transactionData)
                                    }else{
                                        if (transactionData.date!! > dateStart-86400000 &&
                                            transactionData.date!! <= dateEnd){
                                            transactionList.add(transactionData)
                                        }
                                    }
                                }
                            }
                        }
                        "Income" -> {
                            for (transactionSnap in snapshot.children){
                                val transactionData = transactionSnap.getValue(TransactionModel::class.java) //reference data class
                                if (transactionData!!.type == 2){ //income type
                                    if (selectedTimeSpan == "All Time"){
                                        transactionList.add(transactionData)
                                    }else{
                                        if (transactionData.date!! > dateStart-86400000 &&
                                            transactionData.date!! <= dateEnd){
                                            transactionList.add(transactionData)
                                        }
                                    }
                                }
                            }
                        }
                    }
//                    binding.shimmerFrameLayout.visibility = View.VISIBLE
//                    binding.visibilityNoData.visibility = View.GONE
//                    binding.rvTransactionHistory.visibility = View.GONE //hide the recycler view
//                    binding.tvNoData.visibility = View.GONE
//                    binding.noDataImage.visibility = View.GONE
//                    binding.tvNoDataTitle.visibility = View.GONE

                    if (transactionList.isEmpty()){ //if there is no data being displayed
                        binding.noDataImage.visibility = View.VISIBLE
                        binding.tvNoDataTitle.visibility = View.VISIBLE
                        binding.tvNoData.visibility = View.VISIBLE
                        binding.tvNoData.text = "There is no $selectedType data $selectedTimeSpan"
                    }else{
                        val mAdapter = TransactionAdapter(transactionList)
                        binding.rvTransactionHistory.adapter = mAdapter

                        mAdapter.setOnItemClickListener(object: TransactionAdapter.onItemClickListener{ //item click listener and pass extra data
                            override fun onItemClick(position: Int) {
                                val intent = Intent(this@Dashboard.activity, TransactionDetails::class.java)

                                //put extras
                                intent.putExtra("transactionID", transactionList[position].transactionID)
                                intent.putExtra("type", transactionList[position].type)
                                intent.putExtra("title", transactionList[position].title)
                                intent.putExtra("category", transactionList[position].category)
                                intent.putExtra("amount", transactionList[position].amount)
                                intent.putExtra("date", transactionList[position].date)
                                intent.putExtra("note", transactionList[position].note)
                                startActivity(intent)
                            }
                        })
                        binding.rvTransactionHistory.visibility = View.VISIBLE

                    }
                   // binding.shimmerFrameLayout.stopShimmerAnimation()
                    binding.shimmerFrameLayout.visibility = View.GONE
                   // binding.shimmerFrameLayout.visibility = View.GONE
                }else{ //if there is no data in database
                   // binding.shimmerFrameLayout.stopShimmerAnimation()
                    binding.shimmerFrameLayout.visibility = View.GONE

                    binding.noDataImage.visibility = View.VISIBLE
                    binding.tvNoDataTitle.visibility = View.VISIBLE
                    binding.tvNoData.visibility = View.VISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                print("Listener was cancelled")
            }

        })
    }

    private fun getRangeDate(rangeType: Int) {
        val currentDate = Date()
        val cal: Calendar = Calendar.getInstance(TimeZone.getDefault())
        cal.time = currentDate

        val startDay = cal.getActualMinimum(rangeType) //get the first date of the month
        cal.set(rangeType, startDay)
        val startDate = cal.time
        dateStart = startDate.time //convert to millis

        val endDay = cal.getActualMaximum(rangeType) //get the last date of the month
        cal.set(rangeType, endDay)
        val endDate = cal.time
        dateEnd= endDate.time //convert to millis
    }
//    private fun visibilityOptions (){
////        typeOption = requireView().findViewById(R.id.typeSpinner) as Spinner
//        val typeList = arrayOf("All Type", "Expense", "Income")
//        //typeOption.adapter = ArrayAdapter<String>(this.requireActivity(),android.R.layout.simple_list_item_1,options)
//        val typeSpinnerAdapter = ArrayAdapter<String>(this.requireActivity(),android.R.layout.simple_spinner_item,typeList)
//        typeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1)
//        //binding.typeSpinner.adapter = typeSpinnerAdapter
//
////        timeSpanOption = requireView().findViewById(R.id.time) as Spinner
//        val timeSpanList = arrayOf("All Time", "This Month", "This Week", "Today")
//        val timeSpanAdapter = ArrayAdapter<String>(this.requireActivity(),android.R.layout.simple_spinner_item, timeSpanList)
//        timeSpanAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1)
//        //binding.typeSpinner.adapter = timeSpanAdapter
//
////        binding.typeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
////            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
////                when(typeList[p2]){
////                    "All Type" -> selectedType = "All Type"
////                    "Expense" -> selectedType = "Expense"
////                    "Income" -> selectedType = "Income"
////                }
////                getTransactionData()
////            }
////
////            override fun onNothingSelected(p0: AdapterView<*>?) {
////                TODO("Not yet implemented")
////            }
////        }
////
////        binding.timeSpanSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
////            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
////                when(timeSpanList[p2]){
////                    "All Time" -> selectedTimeSpan = "All Time"
////                    "This Month" -> {
////                        selectedTimeSpan = "This Month"
////                        getRangeDate(Calendar.DAY_OF_MONTH)
////                    }
////                    "This Week" -> {
////                        selectedTimeSpan = "This Week"
////                        getRangeDate(Calendar.DAY_OF_WEEK)
////                    }
////                    "Today" -> {
////                        selectedTimeSpan = "Today"
////                        dateStart = System.currentTimeMillis()
////                        dateEnd = System.currentTimeMillis()
////                    }
////                }
////                getTransactionData()
////            }
//
//            override fun onNothingSelected(p0: AdapterView<*>?) {
//                TODO("Not yet implemented")
//            }
//        }
   // }
}