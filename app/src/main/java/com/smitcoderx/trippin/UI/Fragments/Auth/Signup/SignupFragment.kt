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
import java.util.*

class SignupFragment : Fragment(R.layout.fragment_signup) {

    private lateinit var binding: FragmentSignupBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignupBinding.bind(view)

        binding.btnSignup.setOnClickListener {
            if (checkValidations()) {
                binding.transparentLayout.visibility = View.VISIBLE
                binding.loading.visibility = View.VISIBLE
                signup()
            } else {
                Snackbar.make(requireView(), "Please Fill all the Fields", Snackbar.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun signup() {
        val username = binding.tiUsername.editText!!.text.toString().lowercase(Locale.getDefault())
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
                binding.transparentLayout.visibility = View.GONE
                binding.loading.visibility = View.GONE
                if (register!!.message == "user already exists") {
                    Snackbar.make(
                        requireView(),
                        register.message.uppercase(Locale.getDefault()), Snackbar.LENGTH_LONG
                    ).show()
                } else {
                    Snackbar.make(
                        requireView(),
                        register.message.uppercase(Locale.getDefault()), Snackbar.LENGTH_LONG
                    ).show()
                    findNavController().navigate(SignupFragmentDirections.actionSignupFragmentToLoginFragment())
                }
            }

        }
    }

    private fun checkValidations(): Boolean {
        if (binding.tiSignupEmail.editText!!.text.isNullOrEmpty()) {
            binding.tiSignupEmail.error = resources.getString(R.string.field_required)
            return false
        }
        if (binding.tiUsername.editText!!.text.isNullOrEmpty()) {
            binding.tiUsername.error = resources.getString(R.string.field_required)
            return false
        }
        if (binding.tiMobileno.editText!!.text.isNullOrEmpty()) {
            binding.tiMobileno.error = resources.getString(R.string.field_required)
            return false
        }
        if (binding.tiSignupPassword.editText!!.text.isNullOrEmpty()) {
            binding.tiSignupPassword.error = resources.getString(R.string.field_required)
            return false
        } else if (binding.tiSignupPassword.editText!!.text.length < 8) {
            binding.tiSignupPassword.error = resources.getString(R.string.password_limit)
            return false
        }
        return true
    }

}