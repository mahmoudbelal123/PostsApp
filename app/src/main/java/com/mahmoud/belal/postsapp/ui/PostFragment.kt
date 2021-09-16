package com.mahmoud.belal.postsapp.ui

import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mahmoud.belal.postsapp.R
import com.mahmoud.belal.postsapp.core.Resource
import com.mahmoud.belal.postsapp.data.model.Post
import com.mahmoud.belal.postsapp.databinding.FragmentPostBinding
import com.mahmoud.belal.postsapp.presentation.MainViewModel
import com.mahmoud.belal.postsapp.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostFragment : Fragment(R.layout.fragment_post),
    MainAdapter.OnPostItemClickListener {
    private val viewModel by activityViewModels<MainViewModel>()
    private lateinit var mainAdapter: MainAdapter

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
                        "+${result.exception} +"
                    )
                }
            }
        })
    }

    override fun onPostItemClick(post: Post, position: Int) {
        findNavController().navigate(
            PostFragmentDirections.actionPostFragmentToPostDetailsFragment(
                post
            )
        )
    }
}