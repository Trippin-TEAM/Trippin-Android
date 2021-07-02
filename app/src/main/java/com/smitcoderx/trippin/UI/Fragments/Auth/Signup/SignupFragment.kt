package com.smitcoderx.trippin.UI.Fragments.Auth.Signup

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.smitcoderx.trippin.R
import com.smitcoderx.trippin.databinding.FragmentSignupBinding

class SignupFragment : Fragment(R.layout.fragment_signup) {

    private lateinit var binding: FragmentSignupBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignupBinding.bind(view)
    }

}