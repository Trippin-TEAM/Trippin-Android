package com.smitcoderx.trippin.UI.Fragments.PlaceItem

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.appbar.AppBarLayout
import com.smitcoderx.trippin.Adapter.ReviewAdapter
import com.smitcoderx.trippin.R
import com.smitcoderx.trippin.UI.MainActivity
import com.smitcoderx.trippin.databinding.FragmentPlaceBinding

class PlaceFragment : Fragment(R.layout.fragment_place) {

    private lateinit var binding: FragmentPlaceBinding
    private val args by navArgs<PlaceFragmentArgs>()
    private lateinit var reviewAdapter: ReviewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPlaceBinding.bind(view)


        val placeArgs = args.place
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

            fabReviews.setOnClickListener {
                findNavController().navigate(
                    PlaceFragmentDirections.actionPlaceFragmentToReviewFragment(
                        placeArgs._id
                    )
                )
            }
        }

        toolbar(placeArgs.name)
    }

    private fun toolbar(placeName: String) {
        binding.toolbar.apply {
            (activity as MainActivity).setSupportActionBar(this)

            var isShow = true
            var scrollRange = -1
            binding.appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { barLayout, verticalOffset ->
                if (scrollRange == -1) {
                    scrollRange = barLayout?.totalScrollRange!!
                }
                if (scrollRange + verticalOffset == 0) {
                    binding.collapsingToolbarLayout.title = placeName
                    binding.collapsingToolbarLayout.setCollapsedTitleTextColor(resources.getColor(R.color.white))
                    isShow = true
                } else if (isShow) {
                    binding.collapsingToolbarLayout.title =
                        " " //careful there should a space between double quote otherwise it wont work
                    isShow = false
                }
            })
        }
    }

}