package com.smitcoderx.trippin.UI.Fragments.Location

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.smitcoderx.trippin.R
import com.smitcoderx.trippin.databinding.FragmentLocationBinding


class LocationFragment : Fragment(R.layout.fragment_location) {

    private lateinit var binding: FragmentLocationBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLocationBinding.bind(view)
    }


}