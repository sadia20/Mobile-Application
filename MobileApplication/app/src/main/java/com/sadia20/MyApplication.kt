package com.sadia20

import android.app.Application

class MyApplication: Application() {

    var credentialsManager = CredentialsManager

    override fun onCreate() {
        super.onCreate()
        credentialsManager.logIn()
    }
}