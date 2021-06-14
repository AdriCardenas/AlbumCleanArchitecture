package com.android.adriancardenas.app.view.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.android.adriancardenas.app.utils.PARAM_USER
import com.android.adriancardenas.databinding.LoginActivityBinding

class LoginActivity : AppCompatActivity() {

    lateinit var binding: LoginActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupDarkModeSwitch()
        setupSignIn()
    }

    /*
        Simulate Login taking id from user. You can improve it checking if the user exist in our api
     */

    private fun setupSignIn() {
        binding.submit.setOnClickListener {
            val input = binding.userIdInput.text

            if(input!=null && input.toString().isNotEmpty()){
                val intent = Intent(this, AlbumSearchActivity::class.java)
                intent.putExtra(PARAM_USER, input.toString())
                startActivity(intent)
                finish()
            }
        }
    }

    /*
     *  You can in the future store dark mode value in preferences for remind to the app the
     *  status of dark mode
     */
    private fun setupDarkModeSwitch() {
        binding.darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            AppCompatDelegate.setDefaultNightMode(
                if (isChecked) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            )
        }
    }
}