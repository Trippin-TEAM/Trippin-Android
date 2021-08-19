package com.smitcoderx.trippin.UI.Fragments.Auth.Signup

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
import com.smitcoderx.trippin.databinding.FragmentSignupBinding
import retrofit2.HttpException
import java.io.IOException

class SignupFragment : Fragment(R.layout.fragment_signup) {

    private lateinit var binding: FragmentSignupBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignupBinding.bind(view)

        binding.btnSignup.setOnClickListener {
            signup()
        }
    }

    private fun signup() {
        val username = binding.tiUsername.editText!!.text.toString()
        val password = binding.tiSignupPassword.editText!!.text.toString()
        val name = binding.tiName.editText!!.text.toString()
        val email = binding.tiSignupEmail.editText!!.text.toString()
        val mobileNo = binding.tiMobileno.editText!!.text.toString()

        lifecycleScope.launchWhenCreated {
            val response = try {
                ApiClient.retrofitService.registerUser(username, password, name, email, mobileNo)
            } catch (e: IOException) {
                Log.e(TAG, "signup IOException: ${e.message}")
                return@launchWhenCreated
            } catch (e: HttpException) {
                Log.e(TAG, "signup HttpException: ${e.message}")
                return@launchWhenCreated
            }

            if (response.isSuccessful && response.body() != null) {
                val register = response.body()
                Snackbar.make(requireView(), register!!.message, Snackbar.LENGTH_SHORT).show()
                val action = SignupFragmentDirections.actionSignupFragmentToLoginFragment()
                findNavController().navigate(action)
            }

        }
    }

}