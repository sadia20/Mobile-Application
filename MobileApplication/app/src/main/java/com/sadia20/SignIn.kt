package com.sadia20

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.sadia20.mobileapplication.MainActivity
import com.sadia20.mobileapplication.R
import com.sadia20.mobileapplication.databinding.FragmentSignInBinding

class SignIn : Fragment() {

    private var binding: FragmentSignInBinding? = null
    private var credentialsManager = CredentialsManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize credentialsManager(Switch Login and Register activities into fragments + PR)
        credentialsManager = (requireActivity() as MainActivity).credentialsManager // Shared instance

        // Set click listener for the "Register Now" text and using scope function(apply)
        binding?.apply {
            // Navigate to RegisterFragment
            registerNowTv.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, CreateAccount())
                    .addToBackStack(null)
                    .commit()
            }

            // Handle login(Switch Login and Register activities into fragments + PR)
            signInLinear.setOnClickListener {
                val email = inputTextSignIn.text.toString().trim()
                val password = inputPasswordSignIn.text.toString().trim()

                if (validateCredentials(email, password)) {
                    if (credentialsManager.authenticate(email, password)) {
                        navigateToMainActivity()
                    } else {
                        Toast.makeText(requireContext(), "Invalid credentials", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }

        // Set click listener for the "Register Now" text
//        binding?.registerNowTv?.setOnClickListener {
//            findNavController().navigate(R.id.action_signIn_to_createAccount)
//        }

        // Set click listener for the "Next" button
//        binding?.signInLinear?.setOnClickListener {
//            val email = binding?.inputTextSignIn?.text.toString().trim()
//            val password = binding?.inputPasswordSignIn?.text.toString().trim()

//            if (validateCredentials(email, password)) {
//                signInUser(email, password)
//                // Proceed with login logic
//                Toast.makeText(requireContext(), "Login successful", Toast.LENGTH_SHORT).show()
//            }

            // Proceed with login logic(Integrate CredentialsManager into LoginActivity)
//            if (validateCredentials(email, password)) {
//                signInUser(email, password)
//                navigateToMainActivity()
//            } else {
//                Toast.makeText(requireContext(), "Login failed", Toast.LENGTH_SHORT).show()
//            }
//        }
    }

    // Add your validation logic here
    private fun validateCredentials(email: String, password: String): Boolean {
        var isValid = true

        if (!credentialsManager.isEmailValid(email)) {
            binding?.inputTextSignIn?.error = "Invalid email format"
            isValid = false
        } else {
            binding?.inputTextSignIn?.error = null
        }

        if (!credentialsManager.isPasswordValid(password)) {
            binding?.inputPasswordSignIn?.error = "Password cannot be empty"
            isValid = false
        } else {
            binding?.inputPasswordSignIn?.error = null
        }

        return isValid
    }

    // Add your login logic here
    private fun signInUser(email: String, password: String) {
        Toast.makeText(requireContext(), "Sign-in successful!", Toast.LENGTH_SHORT).show()
    }

    // Navigate to MainActivity(Integrate CredentialsManager into LoginActivity)
    private fun navigateToMainActivity() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }
}