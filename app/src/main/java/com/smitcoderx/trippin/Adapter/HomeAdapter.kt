package com.smitcoderx.trippin.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.smitcoderx.trippin.Model.Places.PlacesItem
import com.smitcoderx.trippin.R
import com.smitcoderx.trippin.databinding.ItemLayoutBinding

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    inner class HomeViewHolder(private val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(places: PlacesItem) {
            binding.apply {
                Glide.with(itemView)
                    .load(places.image)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .placeholder(R.drawable.ic_placeholder)
                    .error(R.drawable.main)
                    .into(ivPlaces)

                tvPlaceName.text = places.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val currentItem = differ.currentList[position]

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    val difxferCallback = object : DiffUtil.ItemCallback<PlacesItem>() {
        override fun areItemsTheSame(oldItem: PlacesItem, newItem: PlacesItem) =
            oldItem._id == newItem._id

        override fun areContentsTheSame(oldItem: PlacesItem, newItem: PlacesItem) =
            oldItem == newItem
    }

    val differ = AsyncListDiffer(this, difxferCallback)
}