package com.codehub.finmanager.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.codehub.finmanager.MainActivity
import com.codehub.finmanager.adapters.BudgetAdapter
import com.codehub.finmanager.adapters.TransactionAdapter
import com.codehub.finmanager.databinding.FragmentDashboardBinding
import com.codehub.finmanager.util.Constants

class Dashboard : Fragment() {
    private lateinit var binding: FragmentDashboardBinding
    private lateinit var budgetAdapter: BudgetAdapter
    private lateinit var transactionAdapter: TransactionAdapter

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
        transactionAdapter = TransactionAdapter()
        budgetAdapter.submitList(Constants.budgets)
        transactionAdapter.submitList(Constants.transactions)
        binding.apply {
            rvBudget.apply {
                adapter = budgetAdapter
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            }
            rvTransactionHistory.apply {
                adapter = transactionAdapter
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                isNestedScrollingEnabled = false
            }
        }
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as MainActivity).apply {
            handleBottomBarVisibility(beVisible = true)
            handleBottomBarActions()
        }
    }
}