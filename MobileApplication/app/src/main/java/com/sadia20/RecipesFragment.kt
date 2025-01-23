package com.sadia20

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.sadia20.mobileapplication.R
import com.sadia20.mobileapplication.databinding.FragmentRecipesBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RecipesFragment : Fragment() {
    private var binding: FragmentRecipesBinding? = null
    private val viewModel: RecipesViewModel by viewModels()

    // Initialize the adapter with empty data
//    private val recipeAdapter = RecipesAdapter(
//        emptyList(),
//        itemClickListener = { id ->
//            Toast.makeText(requireContext(), "Item clicked: $id", Toast.LENGTH_SHORT).show()
//            Log.d("RecipesFragment", "Item clicked: $id")
//        },
//        buttonClickListener = { action, id ->
//            Toast.makeText(requireContext(), "$action clicked for item $id", Toast.LENGTH_SHORT)
//                .show()
//            Log.d("RecipesFragment", "$action clicked for item $id")
//        }
//    )

    // Initialize the adapter with empty data
    private val recipeAdapter = RecipeAdapterList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentRecipesBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Load sample recipes
//        loadRecipes()

        // Set up the search view
        setupSearchView()

        // Set up the RecyclerView
        setupRecyclerView()

        // Observe login state for logout navigation
        val credentialsManager = (requireActivity().application as MyApplication).credentialsManager

        // Observe login state for logout navigation
        lifecycleScope.launch {
            credentialsManager.isLoggedIn.collect { isLoggedIn ->
                val navController = Navigation.findNavController(requireView())
                if (!isLoggedIn && navController.currentDestination?.id == R.id.recipesFragment) {
                    navController.navigate(R.id.action_recipesFragment_to_signIn)
                }
            }
        }

        // Logout button functionality
        binding?.logoutButton?.setOnClickListener {
            credentialsManager.logOut()
        }

        // Collect the StateFlow to update the RecyclerView
        lifecycleScope.launch {
            viewModel.recipesStateFlow.collectLatest { recipes ->
                recipeAdapter.submitList(recipes)
            }
        }

        // Observe UI state for recipes and loading
        lifecycleScope.launch {
            viewModel.uiState.collectLatest { uiState ->
                binding?.progressBar?.visibility =
                    if (uiState.isLoading) View.VISIBLE else View.GONE
                binding?.recipesRecyclerView?.visibility =
                    if (uiState.isLoading) View.GONE else View.VISIBLE
                recipeAdapter.submitList(uiState.recipes)
            }
        }
    }

    // Set up the RecyclerView
    private fun setupRecyclerView() {
        binding?.recipesRecyclerView?.layoutManager = LinearLayoutManager(requireContext())
        binding?.recipesRecyclerView?.adapter = recipeAdapter
    }

    // Load sample recipes into the adapter
//    private fun loadRecipes() {
//        val recipes = listOf(
//            RecipeModel(
//                1,
//                "Soup",
//                "Soup recipe.",
//                "https://images.pexels.com/photos/30335678/pexels-photo-30335678/free-photo-of-kirmizi-seramik-kasede-doyurucu-kofte-corbasi.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"
//            )
//        )
//        // Update adapter's data
//        recipeAdapter.updateData(recipes)
//    }

    // Search functionality
    private fun setupSearchView() {
        binding?.recipeSearchView?.setOnQueryTextListener(object :
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { viewModel.searchRecipeName(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { viewModel.searchRecipeName(it) }
                return true
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}