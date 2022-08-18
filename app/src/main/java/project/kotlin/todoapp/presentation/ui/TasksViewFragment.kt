package project.kotlin.todoapp.presentation.ui

import android.app.AlertDialog
import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.getSystemService
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import project.kotlin.todoapp.R
import project.kotlin.todoapp.databinding.AddTaskBinding
import project.kotlin.todoapp.databinding.FragmentTasksViewBinding
import project.kotlin.todoapp.domain.models.Task
import project.kotlin.todoapp.presentation.ui.adaptor.TaskAdaptor
import project.kotlin.todoapp.presentation.viewmodel.TasksViewModel


@AndroidEntryPoint
class TasksViewFragment : Fragment(),TaskAdaptor.OnItemSelected {


    lateinit var binding: FragmentTasksViewBinding
    lateinit var tasksViewModel: TasksViewModel
    lateinit var adaptor: TaskAdaptor
    lateinit var allItemList:ArrayList<Task>

    var opened = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentTasksViewBinding.inflate(layoutInflater)
        tasksViewModel = ViewModelProvider(this)[TasksViewModel::class.java]
        binding.addBtn.isEnabled = true

        if(opened && isNetworkConnected()){
            tasksViewModel.refreshCache()
            opened = false
        }else{
            binding.addBtn.isEnabled = false
            Toast.makeText(context, "Check Internet Connectivity", Toast.LENGTH_SHORT).show()
        }

        tasksViewModel.getTasks().observe(viewLifecycleOwner){ list ->
            if(list != null) {
                adaptor.submitList(list)
                allItemList = list
                binding.progressCircular.visibility = View.GONE
                binding.contentLayout.visibility = View.VISIBLE
            }
        }

        adaptor = TaskAdaptor(this)
        binding.tasksList.adapter = adaptor

        binding.textViewOptions.setOnClickListener{
            onOptionMenuClicked()
        }

        binding.addBtn.setOnClickListener{
            addTask()
        }

        return binding.root
    }

    fun addTask(){
        val myCustomView = AddTaskBinding.inflate(layoutInflater)
        val alertDialog = AlertDialog.Builder(context)
            .setView(myCustomView.root)
            .create()
        alertDialog.show()

        myCustomView.addBtn.setOnClickListener {
            val task = Task("",myCustomView.descriptionEdt.text.toString(),false)
            tasksViewModel.addTask(task)
            binding.progressCircular.visibility = View.VISIBLE
            binding.contentLayout.visibility = View.GONE
            alertDialog.cancel()
        }
        myCustomView.cancelBtn.setOnClickListener {
            alertDialog.cancel()
        }
    }

    private fun onOptionMenuClicked(){
        val popupMenu =
            context?.let {
                PopupMenu(
                    it,
                    binding.textViewOptions
                )
            }
        popupMenu?.inflate(R.menu.menu)

        popupMenu?.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem?): Boolean {
                when (item?.itemId) {
                    R.id.completed_ch -> {
                        filterTasks(true)
                        return true
                    }
                    R.id.not_completed_ch -> {
                        filterTasks(false)
                        return true
                    }
                    R.id.all_list -> {
                        adaptor.submitList(allItemList)
                        return true
                    }
                }
                return false
            }
        })
        popupMenu?.show()
    }

    fun filterTasks(status:Boolean){
        adaptor.submitList(allItemList.filter {
            it.completed == status
        })
    }

    override fun onEditButtonClicked(item: Task) {
        if(isNetworkConnected()) {
            transferTo(item)
        }else{
            Toast.makeText(context, "Check Internet Connectivity", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDeleteButtonClicked(item: Task) {
        if(isNetworkConnected()){
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
                    tasksViewModel.deleteTask(item)

                    dialog.cancel()
                }.setNegativeButton("no") { dialog, _ ->
                    dialog.cancel()
                }
                .create().show()
        }else{
            Toast.makeText(context, "Check Internet Connectivity", Toast.LENGTH_SHORT).show()
        }
    }


    private fun transferTo(task: Task){
        val bundle = Bundle()
        bundle.apply {
            putParcelable("task", task)
        }
        val editTaskFragment = EditTaskFragment()
        editTaskFragment.arguments = bundle
        requireActivity().supportFragmentManager.commit {
            addToBackStack(this.toString())
            replace(R.id.nav_host_fragment, editTaskFragment)
        }
    }

    fun isNetworkConnected(): Boolean {
        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        return connectivityManager!!.activeNetwork != null
    }

}