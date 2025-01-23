package com.sadia20.mobileapplication

import com.sadia20.CredentialsManager
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}

// Add your tests here (Create a Credentials Manager)
class CredentialsManagerTest {

    private val credentialsManager = CredentialsManager

    //Registration - adding accounts to CredentialsManager + PR
    @Test
    fun testValidLoginCredentials() {
        assertTrue(credentialsManager.authenticate("test@te.st", "1234"))
    }

    //Registration - adding accounts to CredentialsManager + PR
    @Test
    fun testInvalidLoginCredentials() {
        assertFalse(credentialsManager.authenticate("wrong@email.com", "wrongpass"))
    }

    //Registration - adding accounts to CredentialsManager + PR
    @Test
    fun testRegisterNewUser() {
        val email = "newuser@example.com"
        val password = "securepassword"
        assertTrue(credentialsManager.register(email, password)) // Registration should succeed
        assertTrue(credentialsManager.authenticate(email, password)) // Should be able to log in
    }

    //Registration - adding accounts to CredentialsManager + PR
    @Test
    fun testRegisterExistingUser() {
        val email = "test@te.st"
        val password = "1234"
        assertFalse(credentialsManager.register(email, password)) // Should fail (already exists)
    }

    @Test
    fun testEmptyEmail() {
        assertFalse(credentialsManager.isEmailValid(""))
    }

    @Test
    fun testWrongFormatEmail() {
        assertFalse(credentialsManager.isEmailValid("invalidemail"))
    }

    @Test
    fun testWellFormattedEmail() {
        assertTrue(credentialsManager.isEmailValid("test@example.com"))
    }

    @Test
    fun testEmptyPassword() {
        assertFalse(credentialsManager.isPasswordValid(""))
    }

    @Test
    fun testFilledPassword() {
        assertTrue(credentialsManager.isPasswordValid("securepassword"))
    }
}