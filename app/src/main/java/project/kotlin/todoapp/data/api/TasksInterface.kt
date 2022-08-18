package project.kotlin.todoapp.data.api

import project.kotlin.todoapp.data.models.Description
import project.kotlin.todoapp.data.models.Status
import project.kotlin.todoapp.data.models.TasksModel
import project.kotlin.todoapp.data.models.TasksModelList
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface TasksInterface {
    @GET("/task")
    suspend fun getTasks(@Header("Authorization") authToken: String): Response<TasksModelList>

    @POST("task")
    suspend fun addTask(
        @Header("Authorization") authToken: String,
        @Body description: Description,
    )

    @PUT("task/{id}")
    suspend fun editTask(
        @Path("id") id:String,
        @Header("Authorization") authToken: String,
        @Body status: Status
    )

    @DELETE("/task/{id}")
    suspend fun deleteTask(
        @Header("Authorization") authToken: String,
        @Path("id") id:String
    )
}