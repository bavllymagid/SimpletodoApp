package project.kotlin.todoapp.data.repository

import project.kotlin.todoapp.data.models.TasksModel

interface TasksRemote {
    suspend fun getTasks(authToken:String) : ArrayList<TasksModel>
    suspend fun addTask(authToken:String,tasksModel: TasksModel)
    suspend fun updateTask(authToken:String,tasksModel: TasksModel)
    suspend fun deleteTask(authToken:String,tasksModel: TasksModel)
}