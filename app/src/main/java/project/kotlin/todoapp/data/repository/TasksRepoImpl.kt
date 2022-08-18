package project.kotlin.todoapp.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.delay
import project.kotlin.todoapp.data.mapper.TaskMapperToData
import project.kotlin.todoapp.data.mapper.TaskMapperToDomain
import project.kotlin.todoapp.data.models.TasksModel
import project.kotlin.todoapp.domain.models.Task
import project.kotlin.todoapp.domain.repository.TasksRepo
import javax.inject.Inject

class TasksRepoImpl @Inject constructor(private val tasksLocalImpl: TasksLocalImpl, private val tasksRemoteImpl: TasksRemoteImpl,
                                        private val taskMapperToData: TaskMapperToData, private val taskMapperToDomain: TaskMapperToDomain)
    : TasksRepo {

    private val authToken:String = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2MmZkNTNkZGU1MDgwYzAwMTcyMDQ4ODUiLCJpYXQiOjE2NjA3NjkzNDN9.OcF5tBBpPPRlTYU7QXtsUafmN1qggtaxRQQVYDntTHM"

    override suspend fun addTaskInDB(task: Task) {
        try {
            tasksRemoteImpl.addTask(authToken, taskMapperToData.toData(task))
            refreshCache(authToken)
        }catch (e:Exception){
            Log.d("MyApp", e.message.toString())
        }
//        tasksLocalImpl.addTask(taskModel)
    }

    override fun getTasksFromDB(): LiveData<ArrayList<Task>> {
        return Transformations.map(tasksLocalImpl.getTasks()){
            taskMapperToDomain.listToDomain(it)
        }

    }

    override suspend fun getTasksFromRetrofit(authToken: String): ArrayList<TasksModel> {
        return tasksRemoteImpl.getTasks(authToken)
    }

    override suspend fun refreshCache(authToken: String) {
        val list = getTasksFromRetrofit(authToken)
        tasksLocalImpl.addTasks(list)
    }

    override suspend fun editTask(task: Task) {
        tasksRemoteImpl.updateTask(authToken,taskMapperToData.toData(task))
        try {
            tasksLocalImpl.update(taskMapperToData.toData(task))
        }catch (e:Exception){
            Log.d("MyApp", e.message.toString())
        }
    }

    override suspend fun deleteTask(task: Task) {
        tasksLocalImpl.deleteTask(taskMapperToData.toData(task))
        try {
            tasksRemoteImpl.deleteTask(authToken, taskMapperToData.toData(task))
        }catch (e:Exception){
            Log.d("MyApp", e.message.toString())
        }
    }
}