package com.smitcoderx.trippin.UI.Fragments.Reviews

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import com.smitcoderx.trippin.API.ApiClient
import com.smitcoderx.trippin.Adapter.ReviewAdapter
import com.smitcoderx.trippin.R
import com.smitcoderx.trippin.Utils.Constants
import com.smitcoderx.trippin.databinding.FragmentReviewBinding
import retrofit2.HttpException
import java.io.IOException

class ReviewFragment : Fragment(R.layout.fragment_review), View.OnClickListener {

    private lateinit var binding: FragmentReviewBinding
    private val args by navArgs<ReviewFragmentArgs>()
    private lateinit var reviewAdapter: ReviewAdapter
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentReviewBinding.bind(view)

        val id = args.id

        setupRv()
        getReviews(id)
        binding.fabAddReview.setOnClickListener(this)
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
                reviewAdapter.differ.submitList(reviews!!.reviews)
            } else {
                binding.rvReview.visibility = View.GONE
                Snackbar.make(
                    binding.reviewLayout,
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

    override fun onClick(v: View?) {
        val filter = AddReviewFragment()
        filter.setTargetFragment(this, 1)
        filter.show(parentFragmentManager, "Dialog")
    }
}