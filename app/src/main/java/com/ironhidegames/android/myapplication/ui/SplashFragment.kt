package com.ironhidegames.android.myapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ironhidegames.android.myapplication.R
import com.ironhidegames.android.myapplication.common.MySharedPreferences
import com.ironhidegames.android.myapplication.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : Fragment() {

    @Inject lateinit var sharedPreferences: MySharedPreferences
    private lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding
            .bind(inflater.inflate(R.layout.fragment_splash, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!sharedPreferences.isFirstAppLaunch)
            findNavController().navigate(R.id.openGameAndClearBackStack)
        else
            initProgressBar()
    }







    private fun initProgressBar() {
        binding.progressBar.progress = 0
        binding.progressBar.max = 100

        lifecycleScope.launch {
            for (i in 0..100) {
                binding.progressBar.progress = i
                delay(50)
            }

            findNavController().navigate(R.id.openWelcomeAndClearBackStack)
        }
    }
}