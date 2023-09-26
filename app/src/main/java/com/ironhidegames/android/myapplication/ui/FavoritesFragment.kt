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
import androidx.navigation.fragment.findNavController
import com.ironhidegames.android.myapplication.R
import com.ironhidegames.android.myapplication.adapters.PlayersAdapter
import com.ironhidegames.android.myapplication.api.players.Response
import com.ironhidegames.android.myapplication.common.OnPlayerClickedListener
import com.ironhidegames.android.myapplication.data.DataHolder
import com.ironhidegames.android.myapplication.databinding.FragmentFavoritesBinding

class FavoritesFragment : Fragment(), OnPlayerClickedListener {

    private lateinit var binding: FragmentFavoritesBinding
    private val holder = DataHolder
    private val list = mutableListOf<Response>()
    private val filteredList = mutableListOf<Response>()
    private lateinit var adapter: PlayersAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding
            .bind(inflater.inflate(R.layout.fragment_favorites, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!isNetworkAvailable())
            findNavController().navigate(R.id.noInternetFragment)

        init()
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





    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }




    private fun init() {
        if (holder.favoriteIds.isNotEmpty()) {
            binding.placeholderText.isVisible = false
            recyclerView()
        }
    }




    private fun recyclerView() {
        list.addAll(collectResponsesList())
        adapter = PlayersAdapter(list, this)
        binding.rvFavorite.adapter = adapter
    }




    private fun collectResponsesList(): List<Response> {
        val list = mutableListOf<Response>()
        holder.favoriteIds.forEach { id ->
            holder.players.forEach {
                it?.response?.forEach { response ->
                    if (response.player.id == id)
                        list.add(response)
                }
            }
        }
        return list
    }

    override fun onPlayerClicked(response: Response) {
        holder.currentPlayer = response
        findNavController().navigate(R.id.playerDetailFragment)
    }
}