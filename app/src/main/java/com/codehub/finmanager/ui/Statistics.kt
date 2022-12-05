package com.codehub.finmanager.ui

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.codehub.finmanager.MainActivity
import com.codehub.finmanager.R
import com.codehub.finmanager.adapters.ChartItemAdapter
import com.codehub.finmanager.databinding.FragmentStatisticsBinding
import com.codehub.finmanager.util.Constants
import kotlinx.coroutines.launch
import org.eazegraph.lib.models.PieModel

class Statistics : Fragment() {
    private lateinit var binding: FragmentStatisticsBinding
    private lateinit var chartItemAdapter: ChartItemAdapter
    private val finManagerViewModel: FinManagerViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (requireActivity() as MainActivity).apply {
            handleBottomBarVisibility(beVisible = true)
            handleBottomBarActions()
        }
        binding = FragmentStatisticsBinding.inflate(inflater)
        return binding.root
    }

    @Suppress("DEPRECATION")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chartItemAdapter = ChartItemAdapter()
        chartItemAdapter.submitList(Constants.chartItems)
        binding.apply {
            rvChartItems.apply {
                adapter = chartItemAdapter
                layoutManager = LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.VERTICAL, false
                )
            }
            Constants.chartItems.forEach { chartItem ->
                val totals = Constants.chartItems.sumOf {
                    it.amount
                }
                val percent = ((chartItem.amount.div(totals) )* 100).toFloat()
                pieChart.apply {
                    addPieSlice(
                        PieModel(
                            chartItem.name,
                            percent,
                            resources.getColor(chartItem.color)
                        )
                    )
                    innerValueString = "55%"
                }

            }
            lifecycleScope.launch {
                finManagerViewModel.currentUser.collect{ user ->
                    tvCurrentUser.text = user.name

                }
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