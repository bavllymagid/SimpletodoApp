package project.kotlin.todoapp.data.repository

import androidx.lifecycle.LiveData
import project.kotlin.todoapp.data.models.TasksModel
import project.kotlin.todoapp.domain.models.Task

interface TasksLocal{
    fun getTasks() : LiveData<List<TasksModel>>
    suspend fun addTask(task: TasksModel)
    suspend fun deleteTask(task: TasksModel)
    suspend fun update(task: TasksModel)
    suspend fun addTasks(tasksList:ArrayList<TasksModel>)
}