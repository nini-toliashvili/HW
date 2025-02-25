package com.example.homework24.presentation.user.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.homework24.data.model.Story
import com.example.homework24.databinding.StoryItemBinding

class StoryAdapter(

) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val storiesMutable = mutableListOf<Story>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val binding = StoryItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return StoryViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return storiesMutable.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        (holder as StoryViewHolder).bind(storiesMutable[position])
    }


    class StoryViewHolder(private val binding: StoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(story: Story) {
            binding.title.setText(story.title)
            Glide.with(binding.root.context)
                .load(story.cover)
                .into(binding.storyImage)
        }
    }

    fun setStories(stories: List<Story>) {
        storiesMutable.clear()
        storiesMutable.addAll(stories)
        notifyDataSetChanged()
    }

}