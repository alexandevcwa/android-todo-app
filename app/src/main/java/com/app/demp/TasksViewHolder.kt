package com.app.demp;

import android.view.View
import android.widget.CheckBox
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class TasksViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    private val tvTask: AppCompatTextView = view.findViewById(R.id.tvTask)
    private val cbTask: CheckBox = view.findViewById(R.id.cbTask)

    fun render(task: Task) {

        // Asigna el estado de la tarea al checkbox
        cbTask.isChecked = task.isSelected

        // Tachar texto de tarea si está seleccionada
        if (task.isSelected) {
            tvTask.paintFlags = tvTask.paintFlags or android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            tvTask.paintFlags =
                tvTask.paintFlags and android.graphics.Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }

        // Asigna el texto de la tarea al textView
        tvTask.text = task.name

        // Asignar color al checkbox según la categoría de la tarea

        val color = when(task.category){
            TaskCategory.Business -> R.color.todo_business_category
            TaskCategory.Other -> R.color.todo_other_category
            TaskCategory.Personal -> R.color.todo_personal_category
        }

        cbTask.buttonTintList = ContextCompat.getColorStateList(cbTask.context, color)
    }

}
