package com.smitcoderx.trippin.Adapter

import android.annotation.SuppressLint
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

class HomeAdapter(private val listener: SetOnClick) :
    RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    inner class HomeViewHolder(private val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = differ.currentList[position]
                    if (item != null) {
                        listener.onClick(item)
                    }
                }
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(places: PlacesItem) {
            binding.apply {
                Glide.with(itemView)
                    .load(places.image)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.no_image)
                    .into(ivPlaces)

                tvPlaceName.text = places.name
                tvRating.text = places.average_ratings.toString()
                tvAddress.text = "${places.address}, ${places.city}"
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

    private val differCallback = object : DiffUtil.ItemCallback<PlacesItem>() {
        override fun areItemsTheSame(oldItem: PlacesItem, newItem: PlacesItem) =
            oldItem._id == newItem._id

        override fun areContentsTheSame(oldItem: PlacesItem, newItem: PlacesItem) =
            oldItem == newItem
    }

    val differ = AsyncListDiffer(this, differCallback)

    interface SetOnClick {
        fun onClick(places: PlacesItem)
    }
}