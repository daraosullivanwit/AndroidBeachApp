package org.wit.beachapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.wit.beachapp.databinding.CardBeachBinding
import org.wit.beachapp.models.BeachModel

interface BeachListener {
    fun onPlacemarkClick(beach: BeachModel)
}

class BeachAdapter constructor(private var beaches: List<BeachModel>,
                               private val listener: BeachListener) :
        RecyclerView.Adapter<BeachAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardBeachBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val placemark = beaches[holder.adapterPosition]
        holder.bind(placemark, listener)
    }

    override fun getItemCount(): Int = beaches.size

    class MainHolder(private val binding : CardBeachBinding) :
            RecyclerView.ViewHolder(binding.root) {

        fun bind(beach: BeachModel, listener: BeachListener) {
            binding.beachTitle.text = beach.title
            binding.description.text = beach.description
            binding.root.setOnClickListener { listener.onPlacemarkClick(beach) }
        }
    }
}