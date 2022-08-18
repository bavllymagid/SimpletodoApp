package project.kotlin.todoapp.presentation.utils

import androidx.recyclerview.widget.DiffUtil
import project.kotlin.todoapp.domain.models.Task

class TasksDiffUtils: DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem == newItem
    }
}