package com.sadia20

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sadia20.mobileapplication.databinding.ItemRepiceBinding

class RecipeAdapterList : ListAdapter<Recipe, RecipeAdapterList.RecipeViewHolder>(RecipeDiffCallback()) {

    inner class RecipeViewHolder(private val binding: ItemRepiceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: Recipe) {
            binding.recipeTitle.text = recipe.title
            binding.recipeDescription.text = recipe.description
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecipeAdapterList.RecipeViewHolder {
        val binding = ItemRepiceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun submitList(list: List<Recipe>?) {
        if (list == currentList) {
            return
        }

        super.submitList(list)
        notifyDataSetChanged()
    }

    class RecipeDiffCallback : DiffUtil.ItemCallback<Recipe>() {
        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem == newItem
        }
    }
}