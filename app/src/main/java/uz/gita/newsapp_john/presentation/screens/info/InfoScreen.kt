package uz.gita.newsapp_john.presentation.screens.info

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.newsapp_john.R
import uz.gita.newsapp_john.databinding.ScreenInfoBinding

class InfoScreen : Fragment(R.layout.screen_info) {
    private val binding: ScreenInfoBinding by viewBinding(ScreenInfoBinding::bind)
    private val args by navArgs<InfoScreenArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data = args.infoData

        binding.apply {
            btnBackInfo.setOnClickListener {
                findNavController().popBackStack()
            }

            tvInfo.text = data.info
            tvText.text = data.text
        }
    }
}