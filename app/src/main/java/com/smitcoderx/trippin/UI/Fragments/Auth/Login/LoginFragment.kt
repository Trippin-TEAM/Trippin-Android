package com.smitcoderx.trippin.UI.Fragments.Auth.Login

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.smitcoderx.trippin.API.ApiClient
import com.smitcoderx.trippin.R
import com.smitcoderx.trippin.Utils.Constants.TAG
import com.smitcoderx.trippin.Utils.PreferenceManager
import com.smitcoderx.trippin.databinding.FragmentLoginBinding
import retrofit2.HttpException
import java.io.IOException

class LoginFragment : Fragment(R.layout.fragment_login) {
    private lateinit var binding: FragmentLoginBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)



        binding.btnLogin.setOnClickListener {
            loginApi()
        }

    }

    private fun loginApi() {
        val email = binding.tiEmail.editText!!.text.toString()
        val password = binding.tiPassword.editText!!.text.toString()
        val prefs = PreferenceManager(requireContext())

        lifecycleScope.launchWhenCreated {
            val response = try {
                Log.d(TAG, "loginApi: $email, $password")
                ApiClient.retrofitService.loginUser(email, password)
            } catch (e: IOException) {
                Log.e(TAG, "IOException: ${e.message}")
                return@launchWhenCreated
            } catch (e: HttpException) {
                Log.e(TAG, "HttpException: ${e.message}")
                return@launchWhenCreated
            }

            if (response.isSuccessful && response.body() != null) {
                val login = response.body()
                prefs.setToken(login!!.token)
                prefs.setLoggedIn(true)
                val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
                findNavController().navigate(action)
            }
        }
    }
}