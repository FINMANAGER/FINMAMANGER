package com.codehub.finmanager.ui

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.codehub.finmanager.MainActivity
import com.codehub.finmanager.R
import com.codehub.finmanager.adapters.ChartItemAdapter
import com.codehub.finmanager.databinding.FragmentStatisticsBinding
import com.codehub.finmanager.util.Constants
import org.eazegraph.lib.models.PieModel

class Statistics : Fragment() {
    private lateinit var binding: FragmentStatisticsBinding
    private lateinit var chartItemAdapter: ChartItemAdapter

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

            pieChart.apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    addPieSlice(
                        PieModel(
                            "Food",
                            55F,
                            resources.getColor(R.color.purple_200, null)
                        )
                    )
                    addPieSlice(
                        PieModel(
                            "Travel",
                            20F,
                            resources.getColor(R.color.teal_200, null)
                        )
                    )
                    addPieSlice(
                        PieModel(
                            "Salary",
                            29F,
                            resources.getColor(R.color.purple_500, null)
                        )
                    )
                    addPieSlice(PieModel("Medicine", 9F, resources.getColor(R.color.teal_700, null)))
                    startAnimation()
                }
                innerValueString = "55%"
            }

        }
    }
}