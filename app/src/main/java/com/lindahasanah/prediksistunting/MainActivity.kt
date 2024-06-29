package com.lindahasanah.prediksistunting

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.lindahasanah.prediksistunting.databinding.ActivityMainBinding
import com.lindahasanah.prediksistunting.network.ApiConfig
import com.lindahasanah.prediksistunting.response.StuntingResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private var selectedGender: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnReset.setOnClickListener {
            binding.edUmur.text?.clear()
            binding.edTinggiBadan.text?.clear()
            binding.rgGender.clearCheck()
        }

        binding.btnHitung.setOnClickListener {
            val selectedGenderId = binding.rgGender.checkedRadioButtonId

            if (selectedGenderId == -1) {
                Toast.makeText(this, "Please select a gender", Toast.LENGTH_SHORT).show()
            } else {
                selectedGender = when (selectedGenderId) {
                    R.id.rb_perempuan -> "1"
                    R.id.rb_lakilaki -> "0"
                    else -> ""
                }
            }
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setMessage("Apakah data yang anda masukkan sudah benar?").setTitle("Konfirmasi")
                .setPositiveButton("Ya, Sudah") { dialog, which ->
                    predict()
                }.setNegativeButton("Periksa lagi") { dialog, which ->
                    dialog.dismiss()
                }

            val dialog: AlertDialog = builder.create()
            dialog.show()
        }

        val dateTimeTextView: TextView = binding.date
        val currentDateTime = getCurrentDateTime()
        dateTimeTextView.text = currentDateTime

    }

    private fun getCurrentDateTime(): String {
        val dateFormat = SimpleDateFormat("dd MMMM yyyy HH.mm", Locale("id", "ID"))
        return dateFormat.format(Date())
    }

    private fun predict() {
        val tinggi = binding.edTinggiBadan.text.toString().toFloat()
        val umur = binding.edUmur.text.toString().toInt()
        val gender = selectedGender.toString()
        val client = ApiConfig.getApiService().getPredict(umur, gender, tinggi)

        client.enqueue(object : Callback<StuntingResponse> {
            override fun onResponse(
                call: Call<StuntingResponse>, response: Response<StuntingResponse>
            ) {
                if (response.isSuccessful) {
                    binding.progressBar.visibility = View.GONE
                    val prediction = response.body()
                    binding.hasil.text = "Kamu dikategorikan :\n${prediction?.result.toString()}"
                    Log.d("MainActivity", "Result: ${prediction?.result}")
                } else {
                    binding.progressBar.visibility = View.GONE
                    Log.e("MainActivity", "Request failed with code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<StuntingResponse>, t: Throwable) {
                Log.e("MainActivity", "Request failed: ${t.message}")
                binding.progressBar.visibility = View.GONE
            }
        })
    }
}