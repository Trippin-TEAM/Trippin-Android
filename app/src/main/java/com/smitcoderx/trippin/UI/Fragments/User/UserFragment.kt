package com.smitcoderx.trippin.UI.Fragments.User

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.smitcoderx.trippin.API.ApiClient
import com.smitcoderx.trippin.R
import com.smitcoderx.trippin.Utils.Constants.TAG
import com.smitcoderx.trippin.Utils.PreferenceManager
import com.smitcoderx.trippin.databinding.FragmentUserBinding
import retrofit2.HttpException
import java.io.IOException

class UserFragment : Fragment(R.layout.fragment_user) {

    private lateinit var binding: FragmentUserBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUserBinding.bind(view)
        val prefs = PreferenceManager(requireContext())
        val token = prefs.getToken()

        getMe(token!!)
    }

    @SuppressLint("SetTextI18n")
    private fun getMe(token: String) {


        lifecycleScope.launchWhenCreated {
            val response = try {
                ApiClient.retrofitService.getMe(token)
            } catch (e: IOException) {
                Log.e(TAG, "getMe IOException: ${e.message}")
                return@launchWhenCreated
            } catch (e: HttpException) {
                Log.e(TAG, "getMe HttpException: ${e.message}")
                return@launchWhenCreated
            }

            if (response.isSuccessful && response.body() != null) {
                val me = response.body()
                binding.tvName.text = me!!.name
                binding.tvUsername.text = "@${me.username}"
            }
        }

    }
}
