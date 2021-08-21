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
import java.util.*

class LoginFragment : Fragment(R.layout.fragment_login) {
    private lateinit var binding: FragmentLoginBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)

        binding.btnLogin.setOnClickListener {
            if (checkValidations()) {
                binding.transparentLayout.visibility = View.VISIBLE
                binding.loading.visibility = View.VISIBLE
                loginApi()
            } else {
                Snackbar.make(requireView(), "Please Fill all the Fields", Snackbar.LENGTH_SHORT)
                    .show()
            }
        }

    }

    private fun loginApi() {
        val prefs = PreferenceManager(requireContext())
        val username = binding.tiEmail.editText!!.text.toString().trim()
            .lowercase(Locale.getDefault())
        val password = binding.tiPassword.editText!!.text.toString().trim()

        lifecycleScope.launchWhenCreated {
            val response = try {
                Log.d(TAG, "loginApi: $username, $password")
                ApiClient.retrofitService.loginUser(username, password)
            } catch (e: IOException) {
                Log.e(TAG, "IOException: ${e.message}")
                return@launchWhenCreated
            } catch (e: HttpException) {
                Log.e(TAG, "HttpException: ${e.message}")
                return@launchWhenCreated
            }

            if (response.isSuccessful && response.body() != null) {
                val login = response.body()
                if (login!!.message == "username or password does not match") {
                    binding.transparentLayout.visibility = View.GONE
                    binding.loading.visibility = View.GONE
                    Snackbar.make(
                        requireView(),
                        login.message.uppercase(Locale.getDefault()), Snackbar.LENGTH_LONG
                    ).show()
                } else {
                    binding.transparentLayout.visibility = View.GONE
                    binding.loading.visibility = View.GONE
                    prefs.setToken(login.token)
                    prefs.setLoggedIn(true)
                    Snackbar.make(
                        requireView(),
                        login.message.uppercase(Locale.getDefault()), Snackbar.LENGTH_LONG
                    ).show()
                    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
                }
            } else {
                binding.transparentLayout.visibility = View.GONE
                binding.loading.visibility = View.GONE
                Snackbar.make(
                    requireView(),
                    "No User Found", Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun checkValidations(): Boolean {
        if (binding.tiEmail.editText!!.text.isNullOrEmpty()) {
            binding.tiPassword.error = resources.getString(R.string.field_required)
            return false
        }
        if (binding.tiEmail.editText!!.text.isNullOrEmpty()) {
            binding.tiPassword.error = resources.getString(R.string.field_required)
            return false
        }
        return true
    }

}