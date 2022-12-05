package com.codehub.finmanager.profile

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
import com.codehub.finmanager.databinding.FragmentCreateProfileBinding
import com.codehub.finmanager.model.UserProfile
import com.codehub.finmanager.ui.FinManagerViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CreateProfileFragment : Fragment() {
    private lateinit var binding: FragmentCreateProfileBinding
    private val finManagerViewModel: FinManagerViewModel by activityViewModels()
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        finManagerViewModel.getUserProfile()

       /* lifecycleScope.launch {
            finManagerViewModel.currentUserProfile.collect{
                binding.apply {
                    tvUser.text = it.fullName
                    tvUserEmail.text =  it.username
                    tiFullName.setText(it.fullName)
                    tiUsername.setText(it.username)
                }
            }
        }*/

        lifecycleScope.launch {
            finManagerViewModel.currentUser.collect{
                binding.apply {
                    tvUser.text = it.name
                    tvUserEmail.text =  it.email
                }
            }
        }



        binding.apply {
            toolbar.setOnMenuItemClickListener {
                    clProfile.visibility = View.VISIBLE
                    clProfileInfo.visibility = View.GONE
                btnLogout.visibility = View.GONE
                true
            }
            btnCancelUpdate.setOnClickListener {
                clProfile.visibility = View.GONE
                clProfileInfo.visibility = View.VISIBLE
                btnLogout.visibility = View.VISIBLE
            }
            btnUpdateProfile.setOnClickListener {
                Toast.makeText(
                    requireContext(),
                    "${tiFullName.text}, ${tiUsername.text}",
                    Toast.LENGTH_SHORT
                ).show()
                finManagerViewModel.updateProfile(UserProfile(
                    fullName = tiFullName.text.toString(),
                    username = tiUsername.text.toString()))
            }
        }
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