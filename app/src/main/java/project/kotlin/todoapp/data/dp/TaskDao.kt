package project.kotlin.todoapp.data.dp

import androidx.lifecycle.LiveData
import androidx.room.*
import project.kotlin.todoapp.data.models.TasksModel

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTasks(list: ArrayList<TasksModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTask(task: TasksModel)

    @Delete
    suspend fun deleteTask(task: TasksModel)

    @Update
    suspend fun updateTask(task: TasksModel)

    @Query("select * from TasksModel")
    fun getTasks() : LiveData<List<TasksModel>>
}