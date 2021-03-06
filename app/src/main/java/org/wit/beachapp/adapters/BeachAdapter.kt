package org.wit.beachapp.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.activity.result.ActivityResultLauncher
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import org.wit.beachapp.R
import org.wit.beachapp.activities.BeachView
import org.wit.beachapp.databinding.CardBeachBinding
import org.wit.beachapp.models.BeachModel

//Handles click events on beach card and allow us to abstract the response to this event.
interface BeachListener {
    fun onBeachClick(beach: BeachModel)
}

class BeachAdapter constructor(private var beaches: List<BeachModel>, private val listener: BeachListener) :
        RecyclerView.Adapter<BeachAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardBeachBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val beach = beaches[holder.adapterPosition]
        holder.bind(beach, listener)
    }

    override fun getItemCount(): Int = beaches.size

    class MainHolder(private val binding : CardBeachBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(beach: BeachModel, listener: BeachListener) {
            binding.beachTitle.text = beach.title
            binding.description.text = beach.description
            binding.size.text = beach.size
            binding.walk.text = beach.walk
            binding.visited.text = beach.visited
            binding.dogs.text = beach.dogs
            binding.fishing.text = beach.fishing
            Picasso.get().load(beach.image).resize(200,200).into(binding.imageIcon)
            binding.root.setOnClickListener { listener.onBeachClick(beach) }
            }
        }
    }
