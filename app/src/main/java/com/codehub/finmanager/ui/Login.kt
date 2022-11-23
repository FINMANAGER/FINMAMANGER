package com.codehub.finmanager.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.codehub.finmanager.MainActivity
import com.codehub.finmanager.R
import com.codehub.finmanager.databinding.FragmentLoginBinding

class Login : Fragment() {
    private lateinit var binding: FragmentLoginBinding
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
                    Toast.makeText(requireContext(), "Heading to dashboard", Toast.LENGTH_SHORT)
                        .show()
                    findNavController().navigate(R.id.dashboard)
                }
            }
            tvSignUp.setOnClickListener {

                findNavController().navigate(R.id.signUp)
            }
        }
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
}