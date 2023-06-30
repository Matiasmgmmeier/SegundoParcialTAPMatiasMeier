package com.example.segundoparcialtapmatiasmeier

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CategoryAdapter(private val categories: List<String>): RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val categoryName: TextView = view.findViewById(R.id.CategoryTextView)
        private val categoryButton: Button = view.findViewById(R.id.SerachCategoryButton)

        fun bind(category: String) {
            categoryName.text = category
            categoryButton.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, FinalJokeActivity::class.java)
                intent.putExtra("category", category)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.itemjokecategory, parent, false))
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categories[position]
        holder.bind(category)
    }
}