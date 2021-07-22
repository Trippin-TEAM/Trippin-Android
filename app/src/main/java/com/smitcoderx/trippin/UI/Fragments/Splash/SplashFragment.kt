package com.smitcoderx.trippin.UI.Fragments.Splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.smitcoderx.trippin.R
import com.smitcoderx.trippin.Utils.PreferenceManager
import com.smitcoderx.trippin.databinding.FragmentSplashBinding
import kotlinx.coroutines.*

class SplashFragment : Fragment(R.layout.fragment_splash) {

    private lateinit var binding: FragmentSplashBinding
    private val splashScope = CoroutineScope(Dispatchers.Main)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /* if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
             activity?.window?.setFlags(
                 WindowManager.LayoutParams.FLAG_FULLSCREEN,
                 WindowManager.LayoutParams.FLAG_FULLSCREEN
             )
         }*/
        binding = FragmentSplashBinding.bind(view)

        val pref = PreferenceManager(requireContext())

        if (pref.getLoggedIn()) {
            splashScope.launch {
                delay(3000)
                val action = SplashFragmentDirections.actionSplashFragmentToHomeFragment()
                findNavController().navigate(action)
            }
        } else {
            splashScope.launch {
                delay(3000)
                val action = SplashFragmentDirections.actionSplashFragmentToLoginSignupFragment()
                findNavController().navigate(action)
            }
        }
    }


    override fun onPause() {
        splashScope.cancel()
        super.onPause()
    }

}