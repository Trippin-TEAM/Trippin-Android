package com.smitcoderx.trippin.UI.Fragments.Home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.smitcoderx.trippin.R
import com.smitcoderx.trippin.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        binding.homeFrag.apply {
            text = "Hello Nigga"
        }
    }

}