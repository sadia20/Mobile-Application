package com.sadia20

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.sadia20.mobileapplication.MainActivity
import com.sadia20.mobileapplication.R
import com.sadia20.mobileapplication.databinding.FragmentCreateAccountBinding

class CreateAccount : Fragment() {
    private var binding: FragmentCreateAccountBinding? = null
    private var credentialsManager = CredentialsManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentCreateAccountBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize credentialsManager
        credentialsManager = (requireActivity() as MainActivity).credentialsManager // Shared instance

        // Set click listener for the "Back to Sign In" text
        binding?.apply {
            // Navigate back to LoginFragment
            backToSignIn.setOnClickListener {
                parentFragmentManager.popBackStack()
            }
        }

        // Set click listener for the "Back to Sign In" text
        binding?.backToSignIn?.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        // Set click listener for the "Create Account" button
        binding?.createAccountLinear?.setOnClickListener {
            val email = binding?.inputTextCA2?.text.toString().trim()
            val password = binding?.passwordTextEditText?.text.toString().trim()

            if (validateCredentials(email, password)) {
                if (credentialsManager.register(email, password)) {
                    Toast.makeText(requireContext(), "Account created successfully!", Toast.LENGTH_SHORT).show()
                    navigateToSignIn()
                } else{
                    binding?.inputTextCA2?.error = "Email already taken"
                }
//                createAccount(email, password)
            }
        }
    }

    // Validate user input
    private fun validateCredentials(email: String, password: String): Boolean {
        var isValid = true

        if (!credentialsManager.isEmailValid(email)) {
            binding?.inputTextCA2?.error = "Invalid email format"
            isValid = false
        } else {
            binding?.inputTextCA2?.error = null
        }

        if (!credentialsManager.isPasswordValid(password)) {
            binding?.passwordTextEditText?.error = "Password cannot be empty"
            isValid = false
        } else {
            binding?.passwordTextEditText?.error = null
        }
        return isValid
    }

    // Create a new account
    private fun createAccount(email: String, password: String) {
        Toast.makeText(requireContext(), "Account created successfully!", Toast.LENGTH_SHORT).show()
    }

    // Navigate back to SignIn
    private fun navigateToSignIn() {
        parentFragmentManager.popBackStack()
    }

}