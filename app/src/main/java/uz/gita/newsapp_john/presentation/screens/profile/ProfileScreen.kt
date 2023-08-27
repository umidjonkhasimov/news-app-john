package uz.gita.newsapp_john.presentation.screens.profile

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.newsapp_john.R
import uz.gita.newsapp_john.data.model.InfoData
import uz.gita.newsapp_john.databinding.ScreenProfileBinding
import uz.gita.newsapp_john.presentation.screens.profile.viewmodel.ProfileViewModel
import uz.gita.newsapp_john.presentation.screens.profile.viewmodel.impl.ProfileViewModelImpl

@AndroidEntryPoint
class ProfileScreen : Fragment(R.layout.screen_profile) {
    private val viewModel: ProfileViewModel by viewModels<ProfileViewModelImpl>()
    private val binding: ScreenProfileBinding by viewBinding(ScreenProfileBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.openLoginScreenLiveData.observe(this, openLoginPageObserver)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.userName.observe(viewLifecycleOwner, userNameObserver)

        binding.logoutProfile.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Logout")
                .setMessage("Are you sure you want to log out?")
                .setPositiveButton("Yes") { _, _ ->
                    viewModel.logout()
                }
                .setNegativeButton("No") { dialog, i ->
                    dialog.dismiss()
                }
                .create()
                .show()
        }

        binding.apply {
            termsProfile.setOnClickListener {
                val info = "Terms & Conditions"
                val text = getString(R.string.terms_conditions)
                val action = ProfileScreenDirections.actionProfileScreenToInfoScreen(InfoData(info, text))
                findNavController().navigate(action)
            }

            privacyProfile.setOnClickListener {
                val info = "Privacy & Policy"
                val text = getString(R.string.privacy_policy)
                val action = ProfileScreenDirections.actionProfileScreenToInfoScreen(InfoData(info, text))
                findNavController().navigate(action)
            }
        }
    }

    private val userNameObserver = Observer<String> {
        binding.tvNameProfile.text = "$it"
    }

    private val openLoginPageObserver = Observer<Unit> {
        findNavController().navigate(R.id.action_profileScreen_to_loginScreen)
    }
}