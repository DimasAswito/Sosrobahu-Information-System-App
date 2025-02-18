package com.polije.sosrobahufactoryapp.ui.role

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.polije.sosrobahufactoryapp.databinding.ActivityRoleBinding
import com.polije.sosrobahufactoryapp.ui.factory.login.LoginActivity

class ChooseRoleActivity : AppCompatActivity() {
    lateinit var binding: ActivityRoleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.cvPabrik.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.cvDistributor.setOnClickListener {
            val intent = Intent(
                this,
                com.polije.sosrobahufactoryapp.ui.distributor.login.LoginActivity::class.java
            )
            startActivity(intent)
        }
    }
}