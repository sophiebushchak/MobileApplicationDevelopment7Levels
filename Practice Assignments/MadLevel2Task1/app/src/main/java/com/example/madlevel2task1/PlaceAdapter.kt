package com.example.madlevel2task1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.madlevel2task1.Data.Place
import com.example.madlevel2task1.databinding.ItemPlaceBinding

class PlaceAdapter(private val places: ArrayList<Place>) :
    RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_place, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return places.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.databind(places[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemPlaceBinding.bind(itemView)

        fun databind(place: Place) {
            binding.tvPlace.text = place.name
            binding.ivPlace.setImageResource(place.imageResId)
        }
    }
}