package uz.gita.newsapp_john.presentation.screens.signup

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.newsapp_john.R
import uz.gita.newsapp_john.databinding.ScreenSignupBinding
import uz.gita.newsapp_john.presentation.screens.signup.viewmodel.SignUpViewModel
import uz.gita.newsapp_john.presentation.screens.signup.viewmodel.impl.SignUpViewModelImpl

@AndroidEntryPoint
class SignUpScreen : Fragment(R.layout.screen_signup) {
    private val binding: ScreenSignupBinding by viewBinding(ScreenSignupBinding::bind)
    private val viewModel: SignUpViewModel by viewModels<SignUpViewModelImpl>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.openHomePage.observe(this, openFillProfilePageObserver)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        attachObservers()

        binding.apply {
            etEmailSignup.doAfterTextChanged {
                val name = etNameSignup.text.toString()
                val email = etEmailSignup.text.toString()
                val password = etPasswordSignup.text.toString()
                viewModel.textChanged(name, email, password)
            }
            etPasswordSignup.doAfterTextChanged {
                val name = etNameSignup.text.toString()
                val email = etEmailSignup.text.toString()
                val password = etPasswordSignup.text.toString()
                viewModel.textChanged(name, email, password)
            }
            btnSignUpSignup.setOnClickListener {
                val name = etNameSignup.text.toString()
                val email = etEmailSignup.text.toString()
                val password = etPasswordSignup.text.toString()
                val isChecked = rememberCheckboxSignup.isChecked
                viewModel.signUp(name, email, password, isChecked)
            }
            btnSignInSignup.setOnClickListener {
                findNavController().navigate(R.id.action_signUpScreen_to_loginScreen)
            }
            btnBackSignup.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun attachObservers() {
        viewModel.showProgress.observe(viewLifecycleOwner, showProgressObserver)
        viewModel.errorHandler.observe(viewLifecycleOwner, errorHandlerObserver)
        viewModel.buttonEnabledState.observe(viewLifecycleOwner, buttonEnabledStateObserver)
    }

    private val showProgressObserver = Observer<Boolean> {
        if (it) {
            binding.customProgressSignup.visibility = View.VISIBLE
        } else {
            binding.customProgressSignup.visibility = View.GONE
        }
    }
    private val openFillProfilePageObserver = Observer<Unit> {
        findNavController().navigate(R.id.action_signUpScreen_to_breakingNewsScreen)
    }
    private val errorHandlerObserver = Observer<String> { error ->
        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
    }
    private val buttonEnabledStateObserver = Observer<Boolean> {
        binding.btnSignUpSignup.isEnabled = it
    }
}