package com.codehub.finmanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.codehub.finmanager.databinding.FragmentLoginBinding

class Login : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentLoginBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnLogin.setOnClickListener {
                if(validFields()){
                    Toast.makeText(requireContext(), "Heading to dashboard", Toast.LENGTH_SHORT)
                        .show()
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