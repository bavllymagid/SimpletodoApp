package project.kotlin.todoapp.data.repository

import android.util.Log
import project.kotlin.todoapp.data.api.TasksInterface
import project.kotlin.todoapp.data.models.Description
import project.kotlin.todoapp.data.models.Status
import project.kotlin.todoapp.data.models.TasksModel
import project.kotlin.todoapp.data.models.TasksModelList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class TasksRemoteImpl @Inject constructor(private val tasksApi :TasksInterface) : TasksRemote {

    override suspend fun getTasks(authToken: String): ArrayList<TasksModel> {
        val response = tasksApi.getTasks(authToken)
        if(response.isSuccessful){
            return tasksApi.getTasks(authToken).body()?.data as ArrayList<TasksModel>
        }
        else{
            Log.d("MyApp", response.message().toString())
        }

        return ArrayList()
    }

    override suspend fun addTask(authToken: String, tasksModel: TasksModel) {
        return tasksApi.addTask(authToken, Description(tasksModel.description))
    }

    override suspend fun updateTask(authToken: String, tasksModel: TasksModel) {
        tasksApi.editTask(tasksModel._id,authToken, Status(tasksModel.completed))
    }

    override suspend fun deleteTask(authToken: String, tasksModel: TasksModel) {
        tasksApi.deleteTask(authToken, tasksModel._id)
    }
}