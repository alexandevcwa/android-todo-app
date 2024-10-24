package com.app.demp

import android.app.Dialog
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class TodoActivity : AppCompatActivity() {

    private lateinit var fabAddTask: FloatingActionButton

    //  Categories
    private lateinit var rvCategories: RecyclerView
    private lateinit var categoriesAdapter: CategoriesAdapter

    // Tasks
    private lateinit var rvTasks: RecyclerView
    private lateinit var tasksAdapter: TasksAdapter

    private val categories = listOf(
        TaskCategory.Business,
        TaskCategory.Personal,
        TaskCategory.Other
    )

    private val tasks : MutableList<Task> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_todo)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initComponent()
        initUI()
        initListeners();
    }


    private fun initComponent() {
        rvCategories = findViewById(R.id.rvCategories)
        rvTasks = findViewById(R.id.rvTasks)
        fabAddTask = findViewById(R.id.fabAddTask)
    }

    // Configuracion de la UI
    private fun initUI() {
        // Adapter para las categorias
        categoriesAdapter = CategoriesAdapter(categories, ::updateCategories)

        // Recycle View de Categorias
        rvCategories.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        rvCategories.adapter = categoriesAdapter


        tasksAdapter = TasksAdapter(tasks, ::onItemSelected)
        rvTasks.layoutManager = LinearLayoutManager(this)
        rvTasks.adapter = tasksAdapter

    }

    private fun initListeners() {
        fabAddTask.setOnClickListener {
            showDialog()
        }
    }

    // Dialogo para agergar tarea a la lista
    private fun showDialog() {

        // Abrir dialogo
        val dialog = Dialog(this);
        dialog.setContentView(R.layout.dialog_add_task)

        // Componentes de dialogo
        val btnAddTask: AppCompatButton =
            dialog.findViewById(R.id.btnAddTask)      // Boton para agregar tarea
        val etTask: AppCompatEditText =
            dialog.findViewById(R.id.etTask)            // Campo para agregar tarea
        val rgCategories: RadioGroup =
            dialog.findViewById(R.id.rgCategories)       // RadioGroup para seleccionar categoria

        // Agregar tarea cuando se presiona el boton de agregar tarea en el dialogo
        btnAddTask.setOnClickListener {
            val taskText = etTask.text.toString()
            if (taskText.isNotEmpty()) {
                val selectedId = rgCategories.checkedRadioButtonId
                val selectedRadioButton: RadioButton = rgCategories.findViewById(selectedId)

                val currentCategory: TaskCategory = when (selectedRadioButton.text) {

                    getString(R.string.todo_app_dialog_rb_negocio) -> TaskCategory.Business

                    getString(R.string.todo_app_dialog_rb_personal) -> TaskCategory.Personal

                    else -> TaskCategory.Other
                }
                tasks.add(
                    Task(
                        etTask.text.toString(),
                        currentCategory
                    )
                )    // Agregar tarea a la lista
                updateTasks()                                               // Actualizar el adaptador de tareas
                dialog.hide()                                               // Cerrar dialogo
                Toast.makeText(
                    dialog.context,
                    "Tarea agregada con éxito",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    dialog.context,
                    "Agrega una descripción a la tarea",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        dialog.show()
    }

    // Actualizar el adaptador de tareas
    private fun updateTasks() {
        val selectedCategories: List<TaskCategory> = categories.filter { it.isSelected }
        val newTasks = tasks.filter { selectedCategories.contains(it.category) }
        tasksAdapter.tasks = newTasks
        tasksAdapter.notifyDataSetChanged();
    }

    private fun onItemSelected(position: Int) {
        updateTasks()
        tasks[position].isSelected = !tasks[position].isSelected
    }

    private fun updateCategories(position: Int) {
        categories[position].isSelected = !categories[position].isSelected
        categoriesAdapter.notifyItemChanged(position)
        updateTasks()
    }
}