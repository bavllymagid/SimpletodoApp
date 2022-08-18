package project.kotlin.todoapp.data.repository

import androidx.lifecycle.LiveData
import project.kotlin.todoapp.data.dp.TaskDao
import project.kotlin.todoapp.data.mapper.TaskMapperToData
import project.kotlin.todoapp.data.mapper.TaskMapperToDomain
import project.kotlin.todoapp.data.models.TasksModel
import project.kotlin.todoapp.domain.models.Task
import javax.inject.Inject

class TasksLocalImpl @Inject constructor(private val taskDB: TaskDao) : TasksLocal {
    override fun getTasks() : LiveData<List<TasksModel>> {
        return taskDB.getTasks()
    }

    override suspend fun addTask(task: TasksModel) {
        taskDB.addTask(task)
    }

    override suspend fun deleteTask(task: TasksModel) {
        taskDB.deleteTask(task)
    }

    override suspend fun update(task: TasksModel) {
        taskDB.updateTask(task)
    }

    override suspend fun addTasks(tasksList: ArrayList<TasksModel>) {
        taskDB.addTasks(tasksList)
    }

}