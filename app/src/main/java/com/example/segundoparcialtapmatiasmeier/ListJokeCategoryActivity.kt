package com.example.segundoparcialtapmatiasmeier

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ListJokeCategoryActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private var categoryList = mutableListOf<String>()
    private lateinit var adapter : CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_joke_category)

        recyclerView = findViewById(R.id.RecyclerCategories)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = CategoryAdapter(categoryList)
        adapter = recyclerView.adapter as CategoryAdapter

        getChuckNorrisCategoryJokes()

    }

    private fun getChuckNorrisCategoryJokes() {
        CoroutineScope(Dispatchers.IO).launch {
            val retrofit = getRetroFit()
            val apiChuckNorris = retrofit.create(ApiChuckNorris::class.java)
            val response = apiChuckNorris.getCategories("jokes/categories")
            runOnUiThread {
            if (response.isSuccessful) {
                val categories: List<String> = response.body() ?: emptyList()
                categoryList.clear()
                categoryList.addAll(categories )
                adapter.notifyDataSetChanged()
            } else {
                showError()
            }
            }
        }
    }
    private fun showError() {
        Toast.makeText(this, "Chuck Norris Fallo en su Conección", Toast.LENGTH_SHORT).show()
    }

    private fun getRetroFit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.chucknorris.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}