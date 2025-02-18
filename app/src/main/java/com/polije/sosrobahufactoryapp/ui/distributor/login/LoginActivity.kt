package com.polije.sosrobahufactoryapp.ui.distributor.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.polije.sosrobahufactoryapp.R

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_distributor)

        supportActionBar?.hide()

    }
}