package com.ironhidegames.android.myapplication.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ironhidegames.android.myapplication.R
import com.ironhidegames.android.myapplication.api.players.Response
import com.ironhidegames.android.myapplication.common.MySharedPreferences
import com.ironhidegames.android.myapplication.data.DataHolder
import com.ironhidegames.android.myapplication.databinding.FragmentPlayerBinding
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PlayerDetailFragment : Fragment() {

    @Inject lateinit var sharedPreferences: MySharedPreferences
    private lateinit var binding: FragmentPlayerBinding
    private val holder = DataHolder

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlayerBinding
            .bind(inflater.inflate(R.layout.fragment_player, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!isNetworkAvailable())
            findNavController().navigate(R.id.noInternetFragment)

        initPlayerFields()
        listeners()
        starAppearence()

    }



    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }


    private fun starAppearence() {
        if (holder.favoriteIds.contains(holder.currentPlayer!!.player.id))
            binding.ivSave.setImageResource(R.drawable.star_half_selected)
        else
            binding.ivSave.setImageResource(R.drawable.star_half)
    }


    private fun listeners() {
        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.ivSave.setOnClickListener {
            if (holder.favoriteIds.contains(holder.currentPlayer!!.player.id)) {
                holder.favoriteIds.remove(holder.currentPlayer!!.player.id)
                binding.ivSave.setImageResource(R.drawable.star_half)
                saveFavoritesToPrefs()
            } else {
                holder.favoriteIds.add(holder.currentPlayer!!.player.id ?: 0)
                binding.ivSave.setImageResource(R.drawable.star_half_selected)
                saveFavoritesToPrefs()
            }
        }
    }





    private fun initPlayerFields() {
        if (holder.currentPlayer != null) {

            Picasso.get().load(holder.currentPlayer!!.player.photo).placeholder(R.drawable.image).into(binding.ivPlayer)
            binding.tvClub.text = holder.currentPlayer!!.statistics[0].team.name
            binding.tvName.text = holder.currentPlayer!!.player.firstname
            binding.tvLastName.text = holder.currentPlayer!!.player.name

            binding.tvAge.text = holder.currentPlayer!!.player.age.toString()

            if (holder.currentPlayer!!.player.birth?.date == null)
                binding.llAge.isVisible = false
            else
                binding.tvBirthDate.text = holder.currentPlayer!!.player.birth?.date

            if (holder.currentPlayer!!.player.birth?.country == null)
                binding.llBirthCountry.isVisible = false
            else
                binding.tvBirthCountry.text = holder.currentPlayer!!.player.birth?.country

            if (holder.currentPlayer!!.player.birth?.place == null)
                binding.llBirthPlace.isVisible = false
            else
                binding.tvBirthPlace.text = holder.currentPlayer!!.player.birth?.place

            if (holder.currentPlayer!!.player.height == null)
                binding.llHeight.isVisible = false
            else
                binding.tvHeight.text = holder.currentPlayer!!.player.height

            if (holder.currentPlayer!!.player.weight == null)
                binding.llWeight.isVisible = false
            else
                binding.tvWeight.text = holder.currentPlayer!!.player.weight


            binding.tvTeamRepeat.text = holder.currentPlayer!!.statistics[0].team.name
            binding.tvLeague.text = holder.currentPlayer!!.statistics[0].league.name
            binding.tvClubCountry.text = holder.currentPlayer!!.statistics[0].league.country
        }
    }




    private fun saveFavoritesToPrefs() {
        val favorites = intListToString(holder.favoriteIds)
        sharedPreferences.favoriteIds = favorites
    }




    private fun intListToString(intList: MutableList<Int>): String {
        return intList.joinToString(",")
    }
}
