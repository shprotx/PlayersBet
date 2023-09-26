package com.ironhidegames.android.myapplication.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ironhidegames.android.myapplication.R
import com.ironhidegames.android.myapplication.adapters.PlayersAdapter
import com.ironhidegames.android.myapplication.api.PlayerApiImplementation
import com.ironhidegames.android.myapplication.api.players.Players
import com.ironhidegames.android.myapplication.api.players.Response
import com.ironhidegames.android.myapplication.common.OnPlayerClickedListener
import com.ironhidegames.android.myapplication.data.DataHolder
import com.ironhidegames.android.myapplication.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(), OnPlayerClickedListener {

    @Inject lateinit var apiImplementation: PlayerApiImplementation
    private val holder: DataHolder = DataHolder
    private lateinit var binding: FragmentHomeBinding
    private val list = mutableListOf<Response>()
    private val filteredList = mutableListOf<Response>()
    private lateinit var adapter: PlayersAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding
            .bind(inflater.inflate(R.layout.fragment_home, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!isNetworkAvailable())
            findNavController().navigate(R.id.noInternetFragment)

        initProgressBar()
        getPlayers()
        showBottomNavigation()
        listeners()

    }





    private fun listeners() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filteredList.clear()
                val filterText = newText.toString().uppercase()
                for (item in list) {
                    if (item.player.name?.uppercase()?.contains(filterText) == true ||
                        item.player.firstname?.uppercase()?.contains(filterText) == true) {
                        filteredList.add(item)
                    }
                }
                adapter.updateAdapterList(filteredList)
                return true
            }
        })
    }





    private fun initProgressBar() {
        if (holder.players.isEmpty()) {
            binding.progress.progress = 0
            binding.progress.max = 100

            lifecycleScope.launch {
                for (i in 0..100) {
                    binding.progress.progress = i
                    if (holder.isAllDataCollected) delay(10)
                    else delay(1000)
                }
                binding.progress.isVisible = false
            }
        }
    }






    private fun getPlayers() {
        if (holder.players.isEmpty() && isNetworkAvailable()) {
            holder.getPlayers(apiImplementation) {
                recyclerViewInit(holder.players)
            }
        } else {
            binding.progress.isVisible = false
            recyclerViewInit(holder.players)
        }
    }




    fun getListForAdapter(allPlayers: List<Players?>): List<Response> {
        val list = mutableListOf<Response>()
        allPlayers.forEach {
            it?.response?.forEach { response ->
                list.add(response)
            }
        }
        holder.isAllDataCollected = true
        return list
    }




    private fun recyclerViewInit(allPlayers: List<Players?>) {
        binding.rvPlayer.layoutManager = LinearLayoutManager(requireContext())
        list.clear()
        list.addAll(getListForAdapter(allPlayers))
        adapter = PlayersAdapter(list, this)
        binding.rvPlayer.adapter = adapter
    }




    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }






    private fun showBottomNavigation() {
        val navigation = requireActivity().findViewById<BottomNavigationView>(R.id.bottomAppNavigation)
        navigation.isVisible = true
    }





    override fun onPlayerClicked(response: Response) {
        holder.currentPlayer = response
        findNavController().navigate(R.id.playerDetailFragment)
    }
}