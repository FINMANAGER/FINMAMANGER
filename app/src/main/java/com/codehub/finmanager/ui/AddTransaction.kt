package com.codehub.finmanager.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.codehub.finmanager.MainActivity
import com.codehub.finmanager.R
import com.codehub.finmanager.adapters.PictureAdapter
import com.codehub.finmanager.databinding.FragmentAddTransactionBinding
import com.codehub.finmanager.util.Constants

class AddTransaction : Fragment() {
    private lateinit var binding: FragmentAddTransactionBinding
    private lateinit var pictureAdapter: PictureAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (requireActivity() as MainActivity).apply {
            handleBottomBarVisibility(beVisible = false)
            handleBottomBarActions()
        }
        binding = FragmentAddTransactionBinding.inflate(inflater)
        pictureAdapter = PictureAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pictureAdapter.submitList(Constants.pitures)
        binding.apply {
            btnSaveTransaction.setOnClickListener {
                findNavController().popBackStack()
            }
            rvPictures.apply {
                adapter = pictureAdapter
            }
            ivBackButton.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as MainActivity).apply {
            handleBottomBarVisibility(beVisible = false)
            handleBottomBarActions()
        }
    }
}