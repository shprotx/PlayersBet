package com.ironhidegames.android.myapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ironhidegames.android.myapplication.R
import com.ironhidegames.android.myapplication.api.players.Player
import com.ironhidegames.android.myapplication.api.players.Response
import com.ironhidegames.android.myapplication.common.OnPlayerClickedListener
import com.ironhidegames.android.myapplication.databinding.ItemPlayerBinding
import com.squareup.picasso.Picasso

class PlayersAdapter(
    private var list: List<Response>,
    private val listener: OnPlayerClickedListener
) : RecyclerView.Adapter<PlayersAdapter.PlayerHolder>() {

    private lateinit var binding: ItemPlayerBinding



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemPlayerBinding.bind(inflater.inflate(R.layout.item_player, parent, false))
        return PlayerHolder(binding)
    }

    override fun onBindViewHolder(holder: PlayerHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateAdapterList(list: MutableList<Response>) {
        this.list = list
        notifyDataSetChanged()
    }



    inner class PlayerHolder(b: ItemPlayerBinding): RecyclerView.ViewHolder(b.root) {
        private val photo = b.ivPlayer
        private val name = b.tvName
        private val country = b.tvCountry
        private val leagueLogo = b.leagueLogo
        private val card = b.card

        fun bind(item: Response) {
            Picasso.get().load(item.player.photo).placeholder(R.drawable.image).into(photo)
            name.text = "${item.player.firstname} ${item.player.name}"
            country.text = item.player.nationality
            Picasso.get().load(item.statistics[0].league.logo).placeholder(R.drawable.league_placeholder).into(leagueLogo)

            card.setOnClickListener {
                listener.onPlayerClicked(list[adapterPosition])
            }
        }
    }

}