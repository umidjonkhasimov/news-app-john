package uz.gita.newsapp_john.presentation.screens.splash

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.newsapp_john.presentation.screens.splash.viewmodel.SplashViewModel
import uz.gita.newsapp_john.presentation.screens.splash.viewmodel.impl.SplashViewModelImpl
import uz.gita.newsapp_john.R

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashScreen : Fragment(R.layout.screen_splash) {
    private val viewModel: SplashViewModel by viewModels<SplashViewModelImpl>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.openLoginPageLD.observe(this, openLoginPageObserver)
        viewModel.openHomePageLD.observe(this, openHomePagePageObserver)
    }

    private val openLoginPageObserver = Observer<Unit> {
        Handler(Looper.getMainLooper()).postDelayed(
            {
                findNavController().navigate(R.id.action_splashScreen_to_loginScreen)
            },
            1500
        )
    }

    private val openHomePagePageObserver = Observer<Unit> {
        Handler(Looper.getMainLooper()).postDelayed(
            {
                findNavController().navigate(R.id.action_splashScreen_to_breakingNewsScreen)
            },
            1500
        )
    }
}