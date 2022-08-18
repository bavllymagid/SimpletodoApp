package project.kotlin.todoapp.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils.replace
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.commit
import dagger.hilt.android.AndroidEntryPoint
import project.kotlin.todoapp.R

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        MenuInflater(this).inflate(R.menu.menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.completed_ch -> {
                val editFragment = EditTaskFragment()
                supportFragmentManager.commit {
                    addToBackStack("AllPosts")
                    replace(R.id.nav_host_fragment, editFragment)
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }
}