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

class MainActivity : AppCompatActivity() {

    private lateinit var searchByCategoryButton: Button
    private lateinit var randomChuckNorrisJoke: TextView
    private lateinit var principalImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchByCategoryButton = findViewById(R.id.ButtonSearchByCatecory)
        randomChuckNorrisJoke = findViewById(R.id.FirstRandomJoke)
        principalImage = findViewById(R.id.PrincipalChuckNorrisImage)
        getChuckNorrisFirstRandomJoke()

        Glide.with(this)
            .load(chuckNorrisPrincipalImage.urlImage)
            .into(principalImage)

        searchByCategoryButton.setOnClickListener {
                val intent = Intent(this, ListJokeCategoryActivity::class.java)
                startActivity(intent)
        }

    }
    private fun getChuckNorrisFirstRandomJoke() {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetroFit().create(ApiChuckNorris::class.java).getJokes("jokes/random")
            val response = call.body()
            runOnUiThread {
                if (call.isSuccessful) {
                    val joke = response?.value
                    randomChuckNorrisJoke.setText(joke)

                } else {
                    showError()
                }
            }
        }
    }
    private fun showError() {
        Toast.makeText(this, "Chuck Norris failed in his mission", Toast.LENGTH_SHORT).show()
    }

    private fun getRetroFit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.chucknorris.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    companion object chuckNorrisPrincipalImage {
        const val urlImage = "https://www.lavanguardia.com/files/content_image_mobile_filter/uploads/2020/03/10/5fa90778dce37.jpeg"
    }

}