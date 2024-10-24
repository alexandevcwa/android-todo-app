package com.app.demp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class CategoriesAdapter(private val categories: List<TaskCategory>, private val onItemSelected: (Int) -> Unit) : RecyclerView.Adapter<CategoriesViewHolder>() {

    // Crear ViewHolder para cada elemento de la lista de categorías
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_todo_category, parent, false)
        return CategoriesViewHolder(view)
    }

    // Obtener el número de elementos en la lista de categorías
    override fun getItemCount(): Int {
        return categories.size
    }

    // Enlazar los datos de cada categoría con el ViewHolder correspondiente
    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        holder.render(categories[position], onItemSelected)
    }
}