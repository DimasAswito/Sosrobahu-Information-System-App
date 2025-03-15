package com.polije.sosrobahufactoryapp.ui.factory.login

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.polije.sosrobahufactoryapp.api.ApiConfig
import com.polije.sosrobahufactoryapp.model.LoginRequest
import com.polije.sosrobahufactoryapp.model.LoginResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FactoryLoginViewModel(private val context: Context) : ViewModel() {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    private val _loginResult = MutableStateFlow<String?>(null)
    val loginResult: StateFlow<String?> get() = _loginResult.asStateFlow()

    fun login(username: String, password: String) {
        val request = LoginRequest(username, password)

        ApiConfig.instance.login(request).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val token = response.body()?.token?.plainTextToken
                    if (token != null) {
                        saveToken(token)
                        _loginResult.value = "success"
                    }
                } else {
                    _loginResult.value = "error"
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _loginResult.value = "error"
            }
        })
    }

    private fun saveToken(token: String) {
        sharedPreferences.edit().putString("auth_token", token).apply()
    }

    fun getToken(): String? {
        return sharedPreferences.getString("auth_token", null)
    }

    fun logout() {
        sharedPreferences.edit().remove("auth_token").apply()
    }
}
