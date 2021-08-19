package com.smitcoderx.trippin.UI.Fragments.Home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.smitcoderx.trippin.API.ApiClient
import com.smitcoderx.trippin.Adapter.HomeAdapter
import com.smitcoderx.trippin.R
import com.smitcoderx.trippin.Utils.Constants.TAG
import com.smitcoderx.trippin.Utils.PreferenceManager
import com.smitcoderx.trippin.databinding.FragmentHomeBinding
import retrofit2.HttpException
import java.io.IOException

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeAdapter: HomeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        val prefs = PreferenceManager(requireContext())

        setupRv()
        getPlaces("Jodhpur")
        binding.tvSearch.setOnClickListener {
            prefs.logoutUser()
            val action = HomeFragmentDirections.actionHomeFragmentToLoginSignupFragment()
            findNavController().navigate(action)
        }

        binding.ivUserImage.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToUserFragment()
            findNavController().navigate(action)
        }
    }

    private fun getPlaces(city: String) {
        lifecycleScope.launchWhenCreated {
            val response = try {
                ApiClient.retrofitService.getPlaces(city)
            } catch (e: IOException) {
                Log.e(TAG, "getMe IO: ${e.message}")
                return@launchWhenCreated
            } catch (e: HttpException) {
                Log.e(TAG, "getMe Http: ${e.message}")
                return@launchWhenCreated
            }

            if (response.isSuccessful && response.body() != null) {
                val place = response.body()
                homeAdapter.differ.submitList(place!!)
            }
        }
    }

    private fun setupRv() {
        homeAdapter = HomeAdapter()
        binding.rvHome.apply {
            setHasFixedSize(true)
            adapter = homeAdapter
        }
    }

}