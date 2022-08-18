package project.kotlin.todoapp.presentation.ui.adaptor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import project.kotlin.todoapp.databinding.TaskItemBinding
import project.kotlin.todoapp.domain.models.Task
import project.kotlin.todoapp.presentation.utils.TasksDiffUtils

class TaskAdaptor(private val onItemSelected: OnItemSelected) : ListAdapter<Task, TaskAdaptor.TasksViewHolder>(TasksDiffUtils()) {


    class TasksViewHolder(val binding: TaskItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(task: Task) {
            binding.apply {
                descriptionTv.text = task.description
                when{
                    !task.completed -> statusTv.text = "Not Completed"
                    else -> statusTv.text = "Completed"
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
        return TasksViewHolder(
            TaskItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        val task = getItem(position)
        holder.bind(task)
        holder.binding.editBtn.setOnClickListener{
            onItemSelected.onEditButtonClicked(task)
        }
        holder.binding.deleteBtn.setOnClickListener{
            onItemSelected.onDeleteButtonClicked(task)
        }
    }

    interface OnItemSelected {
        fun onEditButtonClicked(item:Task)
        fun onDeleteButtonClicked(item:Task)
    }
}