package project.kotlin.todoapp.data.mapper

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import project.kotlin.todoapp.data.models.TasksModel
import project.kotlin.todoapp.domain.models.Task

class TaskMapperToDomain {
    fun toDomain(tasksModel: TasksModel) : Task = Task(
        id = tasksModel._id,
        description = tasksModel.description,
        completed = tasksModel.completed
    )

    fun listToDomain(list :List<TasksModel>) : ArrayList<Task>{
        val listTask = ArrayList<Task>()
        for(task in list){
            listTask.add(toDomain(task))
        }

        return listTask
    }
}