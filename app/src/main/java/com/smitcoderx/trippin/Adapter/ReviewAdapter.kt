package com.smitcoderx.trippin.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.smitcoderx.trippin.Model.Review.ReviewsItem
import com.smitcoderx.trippin.R
import com.smitcoderx.trippin.Utils.Constants.IMAGE_URL
import com.smitcoderx.trippin.databinding.ReviewItemLayoutBinding

class ReviewAdapter : RecyclerView.Adapter<ReviewAdapter.ReviewHolder>() {

    inner class ReviewHolder(private val binding: ReviewItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(review: ReviewsItem) {
            val imageUrl = IMAGE_URL + review.user_id + ".jpg"
            binding.apply {
                Glide.with(itemView)
                    .load(imageUrl)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .placeholder(R.drawable.ic_placeholder)
                    .error(R.drawable.no_image)
                    .into(circleImageView)

                tvName.text = review.name
                tvUsername.text = "@${review.username}"
                tvRating.text = review.ratings.toString()
                tvReview.text = review.review
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewHolder {
        val binding =
            ReviewItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ReviewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewHolder, position: Int) {
        val currentItem = differ.currentList[position]

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val differCallback = object : DiffUtil.ItemCallback<ReviewsItem>() {
        override fun areItemsTheSame(oldItem: ReviewsItem, newItem: ReviewsItem) =
            oldItem._id == newItem._id

        override fun areContentsTheSame(oldItem: ReviewsItem, newItem: ReviewsItem) =
            oldItem == newItem
    }

    val differ = AsyncListDiffer(this, differCallback)
}