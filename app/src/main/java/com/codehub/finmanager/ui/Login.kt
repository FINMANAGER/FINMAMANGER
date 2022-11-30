package com.codehub.finmanager.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.codehub.finmanager.MainActivity
import com.codehub.finmanager.R
import com.codehub.finmanager.databinding.FragmentLoginBinding
import com.codehub.finmanager.model.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Login : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val finManagerViewModel:FinManagerViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity() as MainActivity).handleBottomBarVisibility(false)
        binding = FragmentLoginBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnLogin.setOnClickListener {
                if(validFields()){
                    loginUser()
                }
            }
            tvSignUp.setOnClickListener {
                findNavController().navigate(R.id.signUp)
            }
        }
        observeLogin()
    }

    private fun validFields(): Boolean {
        return when {
            binding.tiEmail.text.isNullOrEmpty() -> {
                binding.tiEmail.error = "Email cannot be empty"
                false
            }
            binding.tiPassword.text.isNullOrEmpty() ->{
                binding.tiPassword.error = "Password cannot be empty"
                false
            }
            else -> {
                true
            }
        }

    }

    private fun loginUser(){
        val user = User(
            email = binding.tiEmail.text.toString(),
            password = binding.tiPassword.text.toString()
        )
        finManagerViewModel.signInUser(user)
    }

    private fun observeLogin(){

        finManagerViewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.pbLogingin.visibility = View.VISIBLE
            }else{
                binding.pbLogingin.visibility = View.GONE
            }

        }
        finManagerViewModel.error.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(requireContext(), "Login failed", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        finManagerViewModel.networkError.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(requireContext(), "No internet connection", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        finManagerViewModel.success.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(requireContext(), "Login successful", Toast.LENGTH_SHORT)
                    .show()
                findNavController().navigate(R.id.dashboard)
            }
        }
    }
}