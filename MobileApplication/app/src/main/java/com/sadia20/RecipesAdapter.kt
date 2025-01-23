package com.sadia20

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sadia20.mobileapplication.databinding.RecipeItemBinding

class RecipesAdapter(
    private var recipes: List<RecipeModel>,
    private val itemClickListener: (Int) -> Unit,
    private val buttonClickListener: (String, Int) -> Unit
) : RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder>() {
    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newRecipes: List<RecipeModel>) {
        recipes = newRecipes
        notifyDataSetChanged()
    }
    inner class RecipeViewHolder(private val binding: RecipeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: RecipeModel) {
            binding.recipeTitle.text = recipe.title
            binding.recipeDescription.text = recipe.description
            binding.likeButton.setOnClickListener {
                buttonClickListener("like", recipe.id)
            }
            binding.shareButton.setOnClickListener {
                buttonClickListener("share", recipe.id)
            }
            binding.root.setOnClickListener {
                itemClickListener(recipe.id)
            }
            Glide.with(binding.recipeImage.context)
                .load(recipe.imageUrl)
                .into(binding.recipeImage)
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecipesAdapter.RecipeViewHolder {
        val binding = RecipeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(binding)
    }
    override fun getItemCount(): Int {
        return recipes.size
    }
    override fun onBindViewHolder(holder: RecipesAdapter.RecipeViewHolder, position: Int) {
        holder.bind(recipes[position])
    }
}