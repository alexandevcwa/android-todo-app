package com.app.demp

data class Task(val name:String, val category: TaskCategory, var isSelected:Boolean = false) {
}