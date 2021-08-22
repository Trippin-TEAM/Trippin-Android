package com.smitcoderx.trippin.UI.Fragments.Search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.smitcoderx.trippin.API.ApiClient
import com.smitcoderx.trippin.Adapter.HomeAdapter
import com.smitcoderx.trippin.Model.Places.PlacesItem
import com.smitcoderx.trippin.R
import com.smitcoderx.trippin.UI.Fragments.Home.HomeFragmentDirections
import com.smitcoderx.trippin.Utils.Constants
import com.smitcoderx.trippin.databinding.FragmentSearchBinding
import retrofit2.HttpException
import java.io.IOException

class SearchFragment : Fragment(R.layout.fragment_search), HomeAdapter.SetOnClick {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var homeAdapter: HomeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)

        setupRv()
        getPlaces("Jodhpur")


        binding.etSearch.editText!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                homeAdapter.filter(s.toString())
            }
        })
    }

    private fun getPlaces(city: String) {
        lifecycleScope.launchWhenCreated {
            val response = try {
                ApiClient.retrofitService.getPlaces(city)
            } catch (e: IOException) {
                Log.e(Constants.TAG, "getMe IO: ${e.message}")
                return@launchWhenCreated
            } catch (e: HttpException) {
                Log.e(Constants.TAG, "getMe Http: ${e.message}")
                return@launchWhenCreated
            }

            if (response.isSuccessful && response.body() != null) {
                val place = response.body()
                homeAdapter.differ.submitList(place!!)
            } else {
                Snackbar.make(binding.homeLayout, "NO Places to See!!", Snackbar.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun setupRv() {
        homeAdapter = HomeAdapter(this)
        binding.rvSearch.apply {
            setHasFixedSize(true)
            adapter = homeAdapter
        }
    }

    override fun onClick(places: PlacesItem) {
        val action = HomeFragmentDirections.actionHomeFragmentToPlaceFragment(places)
        findNavController().navigate(action)
    }
}