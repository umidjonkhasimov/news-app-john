package uz.gita.newsapp_john.presentation.screens.login

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
import uz.gita.newsapp_john.databinding.ScreenLoginBinding
import uz.gita.newsapp_john.presentation.screens.login.viewmodel.LoginViewModel
import uz.gita.newsapp_john.presentation.screens.login.viewmodel.impl.LoginViewModelImpl

@AndroidEntryPoint
class LoginScreen : Fragment(R.layout.screen_login) {
    private val binding: ScreenLoginBinding by viewBinding(ScreenLoginBinding::bind)
    private val viewModel: LoginViewModel by viewModels<LoginViewModelImpl>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.openHomeScreenLD.observe(this, openHomeScreenObserver)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        attachObservers()

        binding.apply {
            etNameLogin.doAfterTextChanged {
                val name = etNameLogin.text.toString()
                val email = etEmailLogin.text.toString()
                val password = etPasswordLogin.text.toString()
                viewModel.textChanged(name, email, password)
            }
            etEmailLogin.doAfterTextChanged {
                val name = etNameLogin.text.toString()
                val email = etEmailLogin.text.toString()
                val password = etPasswordLogin.text.toString()
                viewModel.textChanged(name, email, password)
            }
            etPasswordLogin.doAfterTextChanged {
                val name = etNameLogin.text.toString()
                val email = etEmailLogin.text.toString()
                val password = etPasswordLogin.text.toString()
                viewModel.textChanged(name, email, password)
            }
            btnSignIn.setOnClickListener {
                val name = etNameLogin.text.toString()
                val email = etEmailLogin.text.toString()
                val password = etPasswordLogin.text.toString()
                val isChecked = rememberCheckbox.isChecked
                viewModel.signIn(name, email, password, isChecked)
            }
            btnSignUpLogin.setOnClickListener {
                findNavController().navigate(R.id.action_loginScreen_to_signUpScreen)
            }
            btnBackLogin.setOnClickListener {
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
            binding.customProgressLogin.visibility = View.VISIBLE
        } else {
            binding.customProgressLogin.visibility = View.GONE
        }
    }

    private val openHomeScreenObserver = Observer<Unit> {
        findNavController().navigate(R.id.action_loginScreen_to_breakingNewsScreen)
    }

    private val errorHandlerObserver = Observer<String> { error ->
        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
    }

    private val buttonEnabledStateObserver = Observer<Boolean> {
        binding.btnSignIn.isEnabled = it
    }
}