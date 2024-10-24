package com.app.demp

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class CategoriesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    // Texto de la categoria
    private val tvCategoryName: TextView = view.findViewById(R.id.tvCategoryName)

    // Linea de division de la categoria
    private val viewDivider: View = view.findViewById(R.id.viewDivider)

    // Contenedor de la categoria (CardView)
    private val cardViewContainer = view.findViewById<CardView>(R.id.cardViewContainer)

    // Renderiza la categoria
    fun render(taskCategory: TaskCategory, onItemSelected: (Int) -> Unit) {

        var color = if (taskCategory.isSelected) {
            R.color.todo_background_card
        }else{
            R.color.todo_background_disabled
        }

        cardViewContainer.setCardBackgroundColor(ContextCompat.getColor(cardViewContainer.context, color))

        itemView.setOnClickListener { onItemSelected(layoutPosition) }

        when (taskCategory) {
            TaskCategory.Business -> {
                tvCategoryName.text = "Negocios"
                viewDivider.setBackgroundColor(
                    ContextCompat.getColor(viewDivider.context, R.color.todo_business_category)
                )

            }

            TaskCategory.Other -> {
                tvCategoryName.text = "Otros"
                viewDivider.setBackgroundColor(
                    ContextCompat.getColor(viewDivider.context, R.color.todo_other_category)
                )

            }

            TaskCategory.Personal -> {
                tvCategoryName.text = "Personal"
                viewDivider.setBackgroundColor(
                    ContextCompat.getColor(viewDivider.context, R.color.todo_personal_category)
                )

            }
        }
    }

}