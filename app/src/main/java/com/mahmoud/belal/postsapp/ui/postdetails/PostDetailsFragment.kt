package com.mahmoud.belal.postsapp.ui.postdetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.mahmoud.belal.postsapp.R
import com.mahmoud.belal.postsapp.data.model.Post
import com.mahmoud.belal.postsapp.databinding.FragmentPostDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostDetailsFragment : Fragment(R.layout.fragment_post_details) {
    private lateinit var post: Post

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireArguments().let {
            PostDetailsFragmentArgs.fromBundle(it).also { args ->
                post = args.post
            }
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentPostDetailsBinding.bind(view)
        Glide.with(requireContext())
            .load(post.url)
            .centerCrop()
            .into(binding.imgPostItem)

        binding.txtPostDetailsTitle.text = post.title

    }
}