package project.kotlin.todoapp.data.dp

import androidx.room.Database
import androidx.room.RoomDatabase
import project.kotlin.todoapp.data.models.TasksModel

@Database(entities = [TasksModel::class], version = 1, exportSchema = false)
abstract class TaskDB : RoomDatabase(){
    abstract fun tasksDao(): TaskDao
}