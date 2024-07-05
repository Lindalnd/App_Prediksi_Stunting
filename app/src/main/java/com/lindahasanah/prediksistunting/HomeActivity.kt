package com.lindahasanah.prediksistunting

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.lindahasanah.prediksistunting.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding : ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.checkStunting.setOnClickListener {
            val toCheckStunt = Intent(this@HomeActivity , MainActivity::class.java)
            startActivity(toCheckStunt)
        }

        binding.infoStunting.setOnClickListener {
            val toInfoStunt = Intent(this@HomeActivity , InformationActivity::class.java)
            startActivity(toInfoStunt)
        }
    }
}