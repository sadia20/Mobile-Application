package com.sadia20

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object CredentialsManager {

    // Add your validation logic here
    private val credentialsMap = mutableMapOf<String, String>()

    // Add your validation logic here
    private val _isLoggedIn = MutableStateFlow(false) // Default: not logged in
    val isLoggedIn: StateFlow<Boolean> get() = _isLoggedIn

    // Add your validation logic here
    fun logIn() {
        _isLoggedIn.value = true
    }

    // Add your validation logic here
    fun logOut() {
        _isLoggedIn.value = false
    }

    // Add your validation logic here
    private val validEmail = "test@te.st"
    private val validPassword = "1234"

    // Add your validation logic here(Registration - adding accounts to CredentialsManager + PR)
    private val users = mutableMapOf(
        "test@te.st" to "1234" // Default user
    )

    // Add your validation logic here (Integrate CredentialsManager into LoginActivity)
    fun authenticate(email: String, password: String): Boolean {
        return users[email] == password
    }

    // Add your validation logic here (Registration - adding accounts to CredentialsManager + PR)
    fun register(email: String, password: String): Boolean {
        return if (users.containsKey(email)) {
            false // Email already taken
        } else {
            users[email] = password
            true // Registration successful
        }
    }

    // Add your validation logic here (Create a Credentials Manager)
    fun isEmailValid(email: String): Boolean {
        return email.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    // Add your validation logic here (Create a Credentials Manager)
    fun isPasswordValid(password: String): Boolean {
        return password.isNotEmpty()
    }
}