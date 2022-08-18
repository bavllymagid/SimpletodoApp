package project.kotlin.todoapp.domain.repository

import androidx.lifecycle.LiveData
import project.kotlin.todoapp.data.models.TasksModel
import project.kotlin.todoapp.domain.models.Task

interface TasksRepo {
    suspend fun addTaskInDB(task: Task)
    fun getTasksFromDB() : LiveData<ArrayList<Task>>
    suspend fun getTasksFromRetrofit(authToken:String) : ArrayList<TasksModel>
    suspend fun refreshCache(authToken: String)
    suspend fun editTask(task: Task)
    suspend fun deleteTask(task: Task)
}