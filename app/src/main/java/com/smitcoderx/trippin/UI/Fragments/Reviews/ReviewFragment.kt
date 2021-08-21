package com.smitcoderx.trippin.UI.Fragments.Reviews

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.smitcoderx.trippin.API.ApiClient
import com.smitcoderx.trippin.Adapter.ReviewAdapter
import com.smitcoderx.trippin.R
import com.smitcoderx.trippin.Utils.Constants
import com.smitcoderx.trippin.Utils.Constants.SWIPE_DELAY
import com.smitcoderx.trippin.databinding.FragmentReviewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class ReviewFragment : Fragment(R.layout.fragment_review) {

    private lateinit var binding: FragmentReviewBinding
    private val args by navArgs<ReviewFragmentArgs>()
    private lateinit var reviewAdapter: ReviewAdapter
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private val reviewScope = CoroutineScope(Dispatchers.Main)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentReviewBinding.bind(view)

        val id = args.id

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
        setupRv()
        getReviews(id)
        binding.fabAddReview.setOnClickListener {
            val action = ReviewFragmentDirections.actionReviewFragmentToAddReviewFragment(id)
            findNavController().navigate(action)
        }

        binding.swipeReview.apply {
            setOnRefreshListener {
                reviewScope.launch {
                    delay(SWIPE_DELAY)
                    isRefreshing = false
                }
            }
        }
    }

    private fun getReviews(id: String) {
        lifecycleScope.launchWhenCreated {
            val response = try {
                ApiClient.retrofitService.getReviews(id)
            } catch (e: IOException) {
                Log.e(Constants.TAG, "getReview IO: ${e.message}")
                return@launchWhenCreated
            } catch (e: HttpException) {
                Log.e(Constants.TAG, "getReview Http: ${e.message}")
                return@launchWhenCreated
            }

            if (response.isSuccessful && response.body() != null) {
                val reviews = response.body()
                if (reviews!!.message == "no reviews yet :(") {
                    binding.rvReview.visibility = View.GONE
                    binding.emptyReview.visibility = View.VISIBLE
                    binding.tvNoReview.visibility = View.VISIBLE
                } else {

                    reviewAdapter.differ.submitList(reviews.reviews)
                }
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