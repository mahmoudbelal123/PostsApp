package com.mahmoud.belal.postsapp.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil.DiffResult.NO_POSITION
import androidx.recyclerview.widget.RecyclerView
import com.mahmoud.belal.postsapp.core.BaseViewHolder
import com.mahmoud.belal.postsapp.data.model.Post
import com.mahmoud.belal.postsapp.databinding.ItemPostBinding


class MainAdapter(
    private val context: Context,
    private val postItemClickListener: OnPostItemClickListener
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    private var postsList = listOf<Post>()

    interface OnPostItemClickListener {
        fun onPostItemClick(post: Post, position: Int)
    }

    fun setPostList(postList: List<Post>) {
        this.postsList = postList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding = ItemPostBinding.inflate(LayoutInflater.from(context), parent, false)

        val holder = MainViewHolder(itemBinding)

        itemBinding.root.setOnClickListener {
            val position =
                holder.adapterPosition.takeIf { it != NO_POSITION } ?: return@setOnClickListener
            postItemClickListener.onPostItemClick(postsList[position], position)
        }

        return holder
    }

    override fun getItemCount(): Int = postsList.size

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is MainViewHolder -> holder.bind(postsList[position])
        }
    }

    private inner class MainViewHolder(
        val binding: ItemPostBinding
    ) : BaseViewHolder<Post>(binding.root) {
        override fun bind(item: Post) = with(binding) {
            txtTitlePost.text = item.title
        }
    }
}