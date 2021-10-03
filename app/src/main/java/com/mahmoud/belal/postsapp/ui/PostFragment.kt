package com.mahmoud.belal.postsapp.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.mahmoud.belal.postsapp.BuildConfig
import com.mahmoud.belal.postsapp.R
import com.mahmoud.belal.postsapp.core.Resource
import com.mahmoud.belal.postsapp.data.model.Post
import com.mahmoud.belal.postsapp.databinding.FragmentPostBinding
import com.mahmoud.belal.postsapp.presentation.MainViewModel
import com.mahmoud.belal.postsapp.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_post.*


@AndroidEntryPoint
class PostFragment : Fragment(R.layout.fragment_post),
    MainAdapter.OnPostItemClickListener {
    private val viewModel by activityViewModels<MainViewModel>()
    private lateinit var mainAdapter: MainAdapter
    private lateinit var mFirebaseRemoteConfig: FirebaseRemoteConfig

    private var KEY_BUTTON_MESSAGE = "message"
    private var KEY_LIST_SIZE = "list_size"
    private var KEY_APP_LATEST_VERSION = "app_latest_version"
    private var remoteListSize = "0"
    private var remoteButtonText = "Get"

    private var remoteAppLatestVersion: Long = 0
    private var currentAppVersion = BuildConfig.VERSION_CODE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        mainAdapter = MainAdapter(requireContext(), this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentPostBinding.bind(view)

        binding.recycleViewPosts.layoutManager = LinearLayoutManager(requireContext())
        binding.recycleViewPosts.adapter = mainAdapter


        binding.buttonLoadListSize.setOnClickListener {
            if (binding.editListSize.text.isNullOrEmpty()) {
                binding.editListSize.setError(getString(R.string.hint_enter_size_list))
            } else {
                binding.editListSize.setError(null)
                binding.buttonLoadListSize.setText(remoteButtonText +" " + binding.editListSize.text.toString())
                viewModel.setPost(binding.editListSize.text.toString())
            }


        }

        viewModel.fetchPostsList.observe(viewLifecycleOwner, Observer { result ->
            binding.progressBar.showIf { result is Resource.Loading }

            when (result) {
                is Resource.Loading -> {
                    binding.emptyContainer.root.hide()

                }
                is Resource.Success -> {
                    if (result.data.isEmpty()) {
                        binding.recycleViewPosts.hide()
                        binding.emptyContainer.root.show()
                        binding.progressBar.hide()
                        return@Observer
                    }
                    binding.recycleViewPosts.show()
                    mainAdapter.setPostList(result.data)
                    binding.emptyContainer.root.hide()
                    binding.progressBar.hide()

                }

                is Resource.Failure -> {
                    if (mainAdapter.itemCount == 0) {
                        binding.emptyContainer.root.show()
                        binding.recycleViewPosts.hide()
                    } else {
                        binding.emptyContainer.root.hide()
                    }

                    binding.progressBar.hide()
                    showAlert(
                        getString(R.string.error_internet_connection),
                        "${result.exception}"
                    )
                }
            }
        })

        // firebase remote config setup
        setUpRemoteConfig()
        displayRemoteMessage()

    }

    override fun onPostItemClick(post: Post, position: Int) {
        findNavController().navigate(
            PostFragmentDirections.actionPostFragmentToPostDetailsFragment(
                post
            )
        )
    }

    private fun setUpRemoteConfig() {
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
        val configBuilder = FirebaseRemoteConfigSettings.Builder()
        if (BuildConfig.DEBUG) {
            val cacheInterval: Long = 0
            configBuilder.minimumFetchIntervalInSeconds = cacheInterval
        }
        mFirebaseRemoteConfig.setConfigSettingsAsync(configBuilder.build())
        mFirebaseRemoteConfig.setDefaultsAsync(R.xml.remote_config_defaults);

    }

    private fun displayRemoteMessage() {
        // todo apply your changes here
        mFirebaseRemoteConfig.fetchAndActivate()
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    applyWhenRemoteConfigSuccess()
                } else {
                    Toast.makeText(
                        requireContext(), getString(R.string.remote_config_error),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

    }

    private fun applyWhenRemoteConfigSuccess() {
         remoteListSize = mFirebaseRemoteConfig.getString(KEY_LIST_SIZE)
        edit_list_size.setText("" + remoteListSize)

        remoteButtonText = mFirebaseRemoteConfig.getString(KEY_BUTTON_MESSAGE)
        button_load_list_size.setText("" + remoteButtonText + " " + remoteListSize)

        remoteAppLatestVersion = mFirebaseRemoteConfig.getLong(KEY_APP_LATEST_VERSION)
        if (currentAppVersion < remoteAppLatestVersion) {
            showUpdateApplicationAlert(
                getString(R.string.app_name),
                getString(R.string.update_message_details)
            )
        }

    }
}