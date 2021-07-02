package com.smitcoderx.trippin.UI.Fragments.Auth.Login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.smitcoderx.trippin.R
import com.smitcoderx.trippin.databinding.FragmentLoginBinding

class LoginFragment : Fragment(R.layout.fragment_login) {
    private lateinit var binding: FragmentLoginBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)
    }
}