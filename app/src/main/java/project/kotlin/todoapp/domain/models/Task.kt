package project.kotlin.todoapp.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Task(val id:String, var description: String, var completed: Boolean):Parcelable
