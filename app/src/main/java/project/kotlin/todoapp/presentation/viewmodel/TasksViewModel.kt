package project.kotlin.todoapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import project.kotlin.todoapp.data.repository.TasksRepoImpl
import project.kotlin.todoapp.domain.models.Task
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(private val tasksRepo: TasksRepoImpl): ViewModel() {

    private val authToken:String = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2MmZkNTNkZGU1MDgwYzAwMTcyMDQ4ODUiLCJpYXQiOjE2NjA3NjkzNDN9.OcF5tBBpPPRlTYU7QXtsUafmN1qggtaxRQQVYDntTHM"

    fun getTasks() : LiveData<ArrayList<Task>>{
        return tasksRepo.getTasksFromDB()
    }

    fun addTask(task: Task){
        viewModelScope.launch {
            tasksRepo.addTaskInDB(task)
        }
    }

    fun updateTask(task: Task){
        viewModelScope.launch {
            tasksRepo.editTask(task)
        }
    }

    fun deleteTask(task: Task){
        viewModelScope.launch {
            tasksRepo.deleteTask(task)
        }
    }

    fun refreshCache(){
        viewModelScope.launch {
            tasksRepo.refreshCache(authToken)
        }
    }
}