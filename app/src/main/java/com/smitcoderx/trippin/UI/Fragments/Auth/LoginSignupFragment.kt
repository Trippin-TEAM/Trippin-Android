package com.smitcoderx.trippin.UI.Fragments.Auth

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.smitcoderx.trippin.R
import com.smitcoderx.trippin.databinding.FragmentLoginSignupBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginSignupFragment : Fragment(R.layout.fragment_login_signup) {

    private lateinit var binding: FragmentLoginSignupBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            activity?.window?.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        binding = FragmentLoginSignupBinding.bind(view)

        binding.btnLogin.setOnClickListener {
            val action = LoginSignupFragmentDirections.actionLoginSignupFragmentToLoginFragment()
            findNavController().navigate(action)
        }

        binding.btnSignup.setOnClickListener {
            val action = LoginSignupFragmentDirections.actionLoginSignupFragmentToSignupFragment()
            findNavController().navigate(action)
        }

        binding.tvSkip.setOnClickListener {
            val action = LoginSignupFragmentDirections.actionLoginSignupFragmentToHomeFragment()
            findNavController().navigate(action)
        }

    }


}