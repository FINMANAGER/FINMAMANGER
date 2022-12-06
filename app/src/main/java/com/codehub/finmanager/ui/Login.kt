package com.codehub.finmanager.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.codehub.finmanager.MainActivity
import com.codehub.finmanager.R
import com.codehub.finmanager.databinding.FragmentLoginBinding
import com.codehub.finmanager.model.User
import com.google.firebase.auth.FirebaseAuth

class Login : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val finManagerViewModel: FinManagerViewModel by activityViewModels()
    lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity() as MainActivity).handleBottomBarVisibility(false)
        binding = FragmentLoginBinding.inflate(inflater)
        auth = FirebaseAuth.getInstance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnLogin.setOnClickListener {
                if (validFields()) {
                    loginUser()
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
            binding.tiPassword.text.isNullOrEmpty() -> {
                binding.tiPassword.error = "Password cannot be empty"
                false
            }
            else -> {
                true
            }
        }

    }

    private fun loginUser() {
        val user = User(
            email = binding.tiEmail.text.toString(),
            password = binding.tiPassword.text.toString()
        )
        /*finManagerViewModel.signInUser(user)*/
        binding.pbLogingin.visibility = View.VISIBLE
        auth.signInWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener { taskResults ->
                if (taskResults.isSuccessful) {
                    binding.pbLogingin.visibility = View.GONE
                    findNavController().navigate(R.id.dashboard)
                    val loggedInUser = auth.currentUser?.displayName
                    ?.let { auth.currentUser?.email?.let { it1 -> User(name = it, email = it1) }}
                    if (loggedInUser != null) {
                        finManagerViewModel.setCurrentUser(loggedInUser)
                        finManagerViewModel.setCurrentUserUid(auth.currentUser?.uid!!)
                        finManagerViewModel.getBudgets()
                        finManagerViewModel.getUserProfile()
                        finManagerViewModel.getTransactions()
                        Log.d("Login", "loginUser: ${auth.currentUser?.uid}")
                    }
                } else {
                    binding.pbLogingin.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        "Login failed. ${taskResults.exception?.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }

    private fun observeLogin() {

        finManagerViewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.pbLogingin.visibility = View.VISIBLE
            } else {
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