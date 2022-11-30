package com.codehub.finmanager.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.codehub.finmanager.MainActivity
import com.codehub.finmanager.databinding.FragmentSignUpBinding
import com.codehub.finmanager.model.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SignUp : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    private val finManagerViewModel:FinManagerViewModel by activityViewModels()
    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity() as MainActivity).handleBottomBarVisibility(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            tvLogin.setOnClickListener {
                findNavController().popBackStack()
            }
            btnRegister.setOnClickListener {
                if (validated()){
                     registerUser()
                }
            }
            observeRegistration()
        }
    }

    private fun validated(): Boolean {
        return when {
            binding.tiSignupEmail.text.isNullOrEmpty() -> {
                binding.tiSignupEmail.error = "Email cannot be empty"
                false
            }
            binding.tiSignupPassword.text.isNullOrEmpty() ->{
                binding.tiSignupPassword.error = "Password cannot be empty"
                false
            }
            binding.tiConfirmPassword.text.toString().isEmpty() ->{
                false
            }
            binding.tiConfirmPassword.text.toString()!=binding.tiSignupPassword.text.toString() ->{
                binding.tiConfirmPassword.error = "Passwords do not match"
                false
            }
            else -> {
                true
            }
        }

    }

    private fun registerUser(){
        val user = User(
            email = binding.tiSignupEmail.text.toString(),
            password = binding.tiSignupPassword.text.toString()
        )
        finManagerViewModel.signUpUser(user)
        /*lifecycleScope.launch {
            binding.pbRegistering.visibility = View.VISIBLE
            delay(5000)
            binding.pbRegistering.visibility = View.GONE
            findNavController().popBackStack()
        }*/
    }

    private fun observeRegistration(){

            finManagerViewModel.loading.observe(viewLifecycleOwner) {
                if (it) {
                    binding.pbRegistering.visibility = View.VISIBLE
                }else{
                    binding.pbRegistering.visibility = View.GONE
                }

            }
            finManagerViewModel.error.observe(viewLifecycleOwner) {
                if (it) {
                    Toast.makeText(requireContext(), "Registration failed", Toast.LENGTH_SHORT)
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
                    Toast.makeText(requireContext(), "Registration successful", Toast.LENGTH_SHORT)
                        .show()
                    findNavController().popBackStack()
                }
            }
    }
}
