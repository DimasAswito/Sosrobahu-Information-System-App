package com.polije.sosrobahufactoryapp.ui.role

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.polije.sosrobahufactoryapp.databinding.ActivityRoleBinding
import com.polije.sosrobahufactoryapp.ui.agen.login.AgenLoginActivity
import com.polije.sosrobahufactoryapp.ui.sales.login.SalesLoginActivity

class ChooseRoleActivity : AppCompatActivity() {
    lateinit var binding: ActivityRoleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()


    }
}