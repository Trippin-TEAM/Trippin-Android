package com.smitcoderx.trippin.UI.Fragments.PlaceItem

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.snackbar.Snackbar
import com.smitcoderx.trippin.API.ApiClient
import com.smitcoderx.trippin.Adapter.ReviewAdapter
import com.smitcoderx.trippin.R
import com.smitcoderx.trippin.Utils.Constants.TAG
import com.smitcoderx.trippin.databinding.FragmentPlaceBinding
import retrofit2.HttpException
import java.io.IOException

class PlaceFragment : Fragment(R.layout.fragment_place) {

    private lateinit var binding: FragmentPlaceBinding
    private val args by navArgs<PlaceFragmentArgs>()
    private lateinit var reviewAdapter: ReviewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPlaceBinding.bind(view)

        val placeArgs = args.place
        val id = placeArgs._id
        binding.apply {
            Glide.with(requireContext())
                .load(placeArgs.image)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(R.drawable.no_image)
                .into(ivPlaceitem)

            tvPlaceitemName.text = placeArgs.name
            tvPlaceitemRating.text = placeArgs.average_ratings.toString()
            tvDesc.text = placeArgs.desc
            tvAddress.text = placeArgs.address
            tvCity.text = placeArgs.city
            tvType.text = placeArgs.type
            tvMobileNo.text = placeArgs.mobile_no
            tvEmail.text = placeArgs.email
        }
        setupRv()
        getReviews(id)
    }

    private fun getReviews(id: String) {
        lifecycleScope.launchWhenCreated {
            val response = try {
                ApiClient.retrofitService.getReviews(id)
            } catch (e: IOException) {
                Log.e(TAG, "getReview IO: ${e.message}")
                return@launchWhenCreated
            } catch (e: HttpException) {
                Log.e(TAG, "getReview Http: ${e.message}")
                return@launchWhenCreated
            }

            if (response.isSuccessful && response.body() != null) {
                val reviews = response.body()
                reviewAdapter.differ.submitList(reviews!!.reviews)
            } else {
                binding.rvReview.visibility = View.GONE
                Snackbar.make(
                    binding.profileLayout,
                    "No Reviews Available for your Place",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setupRv() {
        reviewAdapter = ReviewAdapter()
        binding.rvReview.apply {
            setHasFixedSize(true)
            isNestedScrollingEnabled = false
            adapter = reviewAdapter
        }
    }

}