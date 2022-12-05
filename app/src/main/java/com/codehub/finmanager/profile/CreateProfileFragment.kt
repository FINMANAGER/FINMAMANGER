package com.codehub.finmanager.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.codehub.finmanager.MainActivity
import com.codehub.finmanager.R
import com.codehub.finmanager.databinding.FragmentCreateProfileBinding

class CreateProfileFragment : Fragment() {
    private lateinit var binding: FragmentCreateProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
        (requireActivity() as MainActivity).apply {
            handleBottomBarVisibility(beVisible = true)
            handleBottomBarActions()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCreateProfileBinding.inflate(inflater)
        return binding.root
    }

    private fun setupToolbar(){
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_left)
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as MainActivity).apply {
            handleBottomBarVisibility(beVisible = true)
            handleBottomBarActions()
        }
    }
}