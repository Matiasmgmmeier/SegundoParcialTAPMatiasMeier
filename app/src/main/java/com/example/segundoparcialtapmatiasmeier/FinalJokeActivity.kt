package com.example.segundoparcialtapmatiasmeier

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
class FinalJokeActivity : AppCompatActivity()
{
    private lateinit var randomJokeByCategory: TextView
    private lateinit var category: String
    private lateinit var chuckNorrisGunsImage: ImageView
    private lateinit var backToCategoriesMenuButton: Button
    private lateinit var backToMain: Button

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_final_joke)

        val bundle = intent.extras
        category = bundle?.getString("category") ?: ""

        randomJokeByCategory = findViewById(R.id.randomByCategoryJoke)
        chuckNorrisGunsImage = findViewById(R.id.ChuckNorrisImageGuns)
        backToCategoriesMenuButton = findViewById(R.id.BackMenuCategoriesdButton)
        backToMain = findViewById(R.id.BackMain)

        Glide.with(this)
            .load(ChuckNorrisGunsImage.urlImage)
            .into(chuckNorrisGunsImage)

        backToCategoriesMenuButton.setOnClickListener {
            val intent = Intent(this, ListJokeCategoryActivity::class.java)
            startActivity(intent)
        }

        backToMain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        getChuckNorrisRandomByCategoryJoke()
    }

    private fun getChuckNorrisRandomByCategoryJoke() {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetroFit().create(ApiChuckNorris::class.java).getJokes("jokes/random?category=${category}")
            val response = call.body()
            runOnUiThread {
                if (call.isSuccessful) {
                    val joke = response?.value
                    randomJokeByCategory.text = joke
                } else {
                    showError()
                }
            }
        }
    }

    private fun showError() {
        Toast.makeText(this, " The Chuck Norris joke by category failed", Toast.LENGTH_SHORT).show()
    }

    private fun getRetroFit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.chucknorris.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    companion object ChuckNorrisGunsImage {
        const val urlImage = "https://img.europapress.es/fotoweb/fotonoticia_20150310130850-732359_420.jpg"
    }
}









