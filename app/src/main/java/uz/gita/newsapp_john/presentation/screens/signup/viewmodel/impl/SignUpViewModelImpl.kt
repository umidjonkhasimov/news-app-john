package uz.gita.newsapp_john.presentation.screens.signup.viewmodel.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.newsapp_john.data.MySharedPreferences
import uz.gita.newsapp_john.domain.repository.AuthRepository
import uz.gita.newsapp_john.presentation.screens.signup.viewmodel.SignUpViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModelImpl @Inject constructor(
    private val authRepository: AuthRepository,
    private val myPrefs: MySharedPreferences
) : SignUpViewModel, ViewModel() {

    override val showProgress = MutableLiveData(false)
    override val openHomePage = MutableLiveData<Unit>()
    override val errorHandler = MutableLiveData<String>()
    override val buttonEnabledState = MutableLiveData<Boolean>()

    override fun textChanged(name: String, email: String, password: String) {
        buttonEnabledState.value =
            name.trim().isNotEmpty() &&
                    email.endsWith(".com") &&
                    email.length >= 6 &&
                    password.length >= 7
    }

    override fun signUp(name: String, email: String, password: String, remember: Boolean) {
        showProgress.value = true
        authRepository.signUp("", email, password).onEach {
            it.onSuccess {
                showProgress.value = false
                myPrefs.currentUserFirstName = name
                if (remember) {
                    myPrefs.isSignedIn = true
                    myPrefs.currentUserEmail = email
                }
                openHomePage.value = Unit
            }
            it.onFailure { error ->
                showProgress.value = false
                errorHandler.value = error.message
            }
        }.launchIn(viewModelScope)
    }
}