package com.codehub.finmanager.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.codehub.finmanager.MainActivity
import com.codehub.finmanager.databinding.FragmentSignUpBinding

class SignUp : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
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
                    Toast.makeText(requireContext(), "Going dashboard", Toast.LENGTH_SHORT).show()
                }
            }
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
}