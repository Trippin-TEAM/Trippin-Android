package com.smitcoderx.trippin.UI.Fragments.Reviews

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.smitcoderx.trippin.API.ApiClient
import com.smitcoderx.trippin.R
import com.smitcoderx.trippin.Utils.Constants
import com.smitcoderx.trippin.Utils.PreferenceManager
import com.smitcoderx.trippin.databinding.FragmentAddReviewBinding
import retrofit2.HttpException
import java.io.IOException
import java.util.*

class AddReviewFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentAddReviewBinding
    private var rating: String? = ""
    private val args by navArgs<AddReviewFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_review, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddReviewBinding.bind(view)

        val pref = PreferenceManager(requireContext())

        val id = args.id

        binding.ivCancel.setOnClickListener {
            dismiss()
        }

        getTextfromChip()

        binding.btnAddReview.setOnClickListener {
            if (checkValidation(rating!!)) {
                postReview(
                    pref.getToken()!!,
                    id,
                    binding.etReview.text.toString().trim(),
                    rating!!.toFloat()
                )
            } else {
                Toast.makeText(requireContext(), "Please Fill all the Fields", Toast.LENGTH_SHORT)
                    .show()
            }

        }

    }

    private fun checkValidation(rating: String): Boolean {
        if (binding.etReview.text.toString().trim().isEmpty()) {
            binding.etReview.error = resources.getText(R.string.field_required)
            return false
        }
        if (rating == "") {
            Toast.makeText(requireContext(), "Please give Rating", Toast.LENGTH_SHORT)
                .show()
            return false
        }
        return true
    }

    private fun getTextfromChip() {
        val chipGroup: ChipGroup = binding.chipGroup
        for (i in 0 until chipGroup.childCount) {
            val chip = chipGroup.getChildAt(i) as Chip
            chip.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    rating = chip.text.toString().trim()
                }
            }
        }
    }

    private fun postReview(token: String, businessId: String, review: String, rating: Float) {
        lifecycleScope.launchWhenCreated {
            val response = try {
                ApiClient.retrofitService.postReview(token, businessId, review, rating)
            } catch (e: IOException) {
                Log.e(Constants.TAG, "getReview IO: ${e.message}")
                return@launchWhenCreated
            } catch (e: HttpException) {
                Log.e(Constants.TAG, "getReview Http: ${e.message}")
                return@launchWhenCreated
            }
            if (response.isSuccessful && response.body() != null) {
                val reviewMessage = response.body()
                if (reviewMessage!!.message == "Cannot review same place multiple times") {
                    Toast.makeText(
                        requireContext(),
                        reviewMessage.message.uppercase(Locale.getDefault()),
                        Toast.LENGTH_SHORT
                    ).show()
                    dismiss()
                } else {
                    Toast.makeText(
                        requireContext(),
                        reviewMessage.message.uppercase(Locale.getDefault()),
                        Toast.LENGTH_SHORT
                    ).show()
                    dismiss()
                }
            }
        }
    }
}