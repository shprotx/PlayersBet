package com.ironhidegames.android.myapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ironhidegames.android.myapplication.R
import com.ironhidegames.android.myapplication.common.MySharedPreferences
import com.ironhidegames.android.myapplication.databinding.FragmentWelcomeBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WelcomeFragment : Fragment() {

    @Inject lateinit var sharedPreferences: MySharedPreferences
    private lateinit var binding: FragmentWelcomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWelcomeBinding
            .bind(inflater.inflate(R.layout.fragment_welcome, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listeners()
    }


    private fun listeners() {
        binding.bt1.setOnClickListener {
            sharedPreferences.isFirstAppLaunch = false
            findNavController().navigate(R.id.openGameAndClearBackStack1)
        }
    }
}