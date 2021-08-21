package com.smitcoderx.trippin.UI.Fragments.Reviews

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.smitcoderx.trippin.R
import com.smitcoderx.trippin.databinding.FragmentAddReviewBinding

class AddReviewFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentAddReviewBinding
    private lateinit var rating: String

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

        binding.ivCancel.setOnClickListener {
            dismiss()
        }

        binding.btnAddReview.setOnClickListener {
            Toast.makeText(requireContext(), rating, Toast.LENGTH_SHORT).show()
        }

    }

    private fun getTextfromChip() {
        val chipGroup: ChipGroup = binding.chipGroup
        for (i in 0 until chipGroup.childCount) {
            val chip = chipGroup.getChildAt(i) as Chip
            chip.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    rating = chip.text.toString().trim()
                    if (chip.text.toString() == "") {
                        Toast.makeText(requireContext(), "Please give Rating", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Toast.makeText(requireContext(), rating, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

}