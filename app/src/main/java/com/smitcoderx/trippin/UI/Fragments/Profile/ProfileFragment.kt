package com.smitcoderx.trippin.UI.Fragments.Profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.snackbar.Snackbar
import com.smitcoderx.trippin.API.ApiClient
import com.smitcoderx.trippin.R
import com.smitcoderx.trippin.UI.MainActivity
import com.smitcoderx.trippin.Utils.Constants.TAG
import com.smitcoderx.trippin.Utils.PreferenceManager
import com.smitcoderx.trippin.databinding.FragmentProfileBinding
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.util.*

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var binding: FragmentProfileBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)
        toolbar()

        val prefs = PreferenceManager(requireContext())
        val token = prefs.getToken()

        getMe(token!!)

        binding.fabEdit.setOnClickListener {
            allEnabled()
        }

        binding.fabSave.setOnClickListener {
            allDisabled()
            updateUser(token)
        }

        binding.fabCamera.setOnClickListener {
            findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToUploadImageActivity())
        }

        binding.btnLogout.setOnClickListener {
            prefs.logoutUser()
            findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToLoginFragment())
        }

    }

    private fun toolbar() {
        binding.toolbar.apply {
            (activity as MainActivity).setSupportActionBar(this)

            var isShow = true
            var scrollRange = -1
            binding.appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { barLayout, verticalOffset ->
                if (scrollRange == -1) {
                    scrollRange = barLayout?.totalScrollRange!!
                }
                if (scrollRange + verticalOffset == 0) {
                    binding.collapsingToolbarLayout.title = resources.getString(R.string.profile)
                    binding.collapsingToolbarLayout.setCollapsedTitleTextColor(resources.getColor(R.color.white))
                    binding.fabCamera.hide()
                    isShow = true
                } else if (isShow) {
                    binding.collapsingToolbarLayout.title =
                        " "
                    binding.fabCamera.show()
                    isShow = false
                }
            })
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getMe(token: String) {
        lifecycleScope.launchWhenCreated {
            val response = try {
                ApiClient.retrofitService.getMe(token)
            } catch (e: IOException) {
                Log.e(TAG, "getMe IO: ${e.message}")
                return@launchWhenCreated
            } catch (e: HttpException) {
                Log.e(TAG, "getMe Http: ${e.message}")
                return@launchWhenCreated
            }

            if (response.isSuccessful && response.body() != null) {
                val user = response.body()

                binding.apply {
                    tiMeName.editText!!.setText(user!!.name)
                    tiMeEmail.editText!!.setText(user.email)
                    tiMeUsername.editText!!.setText("@${user.username}")
                    tiMeMobileno.editText!!.setText(user.mobile_no)
                    tiMeDesc.editText!!.setText(user.desc)
                    Glide.with(requireContext())
                        .load(user.image)
                        .centerCrop()
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .error(R.drawable.no_image)
                        .into(ivMe)
                }
            }
        }
    }

    private fun updateUser(token: String) {
        lifecycleScope.launchWhenCreated {
            val updateUser = JSONObject()
            updateUser.put("name", binding.tiMeName.editText!!.text.toString().trim())
            updateUser.put("mobile_no", binding.tiMeMobileno.editText!!.text.toString().trim())
            updateUser.put("desc", binding.tiMeDesc.editText!!.text.toString().trim())
            val requestBody =
                RequestBody.create(MediaType.parse("application/json"), updateUser.toString())
            val response = try {
                ApiClient.retrofitService.updateUser(token, requestBody)
            } catch (e: IOException) {
                Log.e(TAG, "getMe IO: ${e.message}")
                return@launchWhenCreated
            } catch (e: HttpException) {
                Log.e(TAG, "getMe Http: ${e.message}")
                return@launchWhenCreated
            }
            if (response.isSuccessful && response.body() != null) {
                val updateUserData = response.body()
                Snackbar.make(
                    requireView(),
                    updateUserData!!.message.uppercase(Locale.getDefault()), Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun allEnabled() {
        binding.apply {
            fabSave.show()
            fabEdit.hide()
            tiMeName.isEnabled = true
            tiMeMobileno.isEnabled = true
            tiMeDesc.isEnabled = true
        }
    }

    private fun allDisabled() {
        binding.apply {
            fabEdit.show()
            fabSave.hide()
            tiMeName.isEnabled = false
            tiMeMobileno.isEnabled = false
            tiMeDesc.isEnabled = false
        }
    }
}