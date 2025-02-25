package com.example.homework24.presentation.user.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.homework24.databinding.PostItemBinding
import com.example.homework24.presentation.user.model.PostUiModel

class PostAdapter : ListAdapter<PostUiModel, PostAdapter.PostViewHolder>(DIFF_CALLBACK) {

    inner class PostViewHolder(private val binding: PostItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(post : PostUiModel) {
            binding.postDate.text = post.formattedDate
            binding.postText.text = post.title
            binding.username.text = post.ownerFullName
            binding.commentsQuantity.text = post.comments
            binding.likesQuantity.text = post.likes
            Glide.with(binding.root.context)
                .load(post.ownerProfile)
                .into(binding.profileImage)

            loadImages(post.images, binding)
        }
    }


    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PostUiModel>() {
            override fun areItemsTheSame(oldItem: PostUiModel, newItem: PostUiModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: PostUiModel, newItem: PostUiModel): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding= PostItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }







    private fun loadImages(images: List<String>, binding: PostItemBinding) {
        when (images.size) {
            0 -> {
                binding.threeImagesCase.visibility = View.INVISIBLE
                binding.oneImageCaseImageView.visibility = View.INVISIBLE
                binding.twoImagesCase.visibility = View.INVISIBLE
            }
            1 -> {
                binding.oneImageCaseImageView.visibility = View.VISIBLE
                binding.twoImagesCase.visibility = View.GONE
                binding.threeImagesCase.visibility = View.GONE
                Glide.with(binding.root.context)
                    .load(images[0])
                    .into(binding.oneImageCaseImageView)

            }
            2 -> {
                binding.twoImagesCase.visibility = View.VISIBLE
                binding.oneImageCaseImageView.visibility = View.GONE
                binding.threeImagesCase.visibility = View.GONE
                Glide.with(binding.root.context)
                    .load(images[0])
                    .into(binding.twoImagesOne)

                Glide.with(binding.root.context)
                    .load(images[1])
                    .into(binding.twoImagesTwo)
            }

            3 -> {
                binding.threeImagesCase.visibility = View.VISIBLE
                binding.oneImageCaseImageView.visibility = View.GONE
                binding.twoImagesCase.visibility = View.GONE

                Glide.with(binding.root.context)
                    .load(images[0])
                    .into(binding.threeImagesOne)

                Glide.with(binding.root.context)
                    .load(images[1])
                    .into(binding.threeImagesTwo)

                Glide.with(binding.root.context)
                    .load(images[2])
                    .into(binding.threeImagesThree)
            }
        }
    }
}

