package project.kotlin.todoapp.data.mapper

import androidx.room.PrimaryKey
import project.kotlin.todoapp.data.models.TasksModel
import project.kotlin.todoapp.domain.models.Task

class TaskMapperToData {
    fun toData(task: Task) : TasksModel = TasksModel(0,
    task.id,
    task.completed,
    "",
    task.description,
    "",
    "")
}