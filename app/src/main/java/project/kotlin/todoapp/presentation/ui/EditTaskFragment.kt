package project.kotlin.todoapp.presentation.ui

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import project.kotlin.todoapp.R
import project.kotlin.todoapp.databinding.FragmentEditTaskBinding
import project.kotlin.todoapp.databinding.FragmentTasksViewBinding
import project.kotlin.todoapp.domain.models.Task
import project.kotlin.todoapp.presentation.viewmodel.TasksViewModel

@AndroidEntryPoint
class EditTaskFragment : Fragment() {

    private lateinit var binding: FragmentEditTaskBinding
    lateinit var tasksViewModel: TasksViewModel
    lateinit var task :Task

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var state = false
        binding = FragmentEditTaskBinding.inflate(layoutInflater)
        tasksViewModel = ViewModelProvider(this)[TasksViewModel::class.java]

        task = arguments?.getParcelable<Task>("task")!!

        binding.descriptionEdt.setText(task.description)
        when(task.completed){
            false -> {
                binding.radioButton1.isChecked = false
                binding.radioButton2.isChecked = true
            }
            true -> {
                binding.radioButton1.isChecked = true
                binding.radioButton2.isChecked = false
            }
        }

        binding.saveBtn.setOnClickListener{
            task.description = binding.descriptionEdt.text.toString()
            task.completed = state
            task.let { it1 -> tasksViewModel.updateTask(it1) }
            transferTo()
        }

        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            state = when(checkedId){
                R.id.radio_button_1 -> true
                else -> false
            }
        }

        binding.deleteBtn.setOnClickListener {
            deleteTask(task)
        }

        return binding.root
    }

    fun deleteTask(task: Task){
        AlertDialog.Builder(context)
            .setTitle("Are you Sure you want to delete it?")
            .setIcon(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_baseline_delete_24,
                    activity?.theme
                )
            )
            .setPositiveButton("yes") { dialog, _ ->
                tasksViewModel.deleteTask(task)
                transferTo()
                dialog.cancel()
            }.setNegativeButton("no") { dialog, _ ->
                dialog.cancel()
            }
            .create().show()
    }

    private fun transferTo(){
        val viewAll = TasksViewFragment()
        requireActivity().supportFragmentManager.commit {
            addToBackStack(this.toString())
            replace(R.id.nav_host_fragment, viewAll)
        }
    }



}