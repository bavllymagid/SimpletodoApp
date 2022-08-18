package project.kotlin.todoapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TasksModel(
    val __v: Int,
    @PrimaryKey
    val _id: String,
    val completed: Boolean,
    val createdAt: String,
    val description: String,
    val owner: String,
    val updatedAt: String
)