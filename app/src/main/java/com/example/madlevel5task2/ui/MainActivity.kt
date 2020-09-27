package com.example.madlevel5task2.ui

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.madlevel5task2.R

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        navController = findNavController(R.id.nav_host_fragment)
        toolbarState()
    }

    /**
     * This listens to changes in the current Fragment.
     * If the Fragment is AddGameFragment, add a back button to the toolbar and set the title to
     * "Add Game"
     * If the new Fragment is not AddGameFragment, remove the back button and set the title back to
     * "Game Backlog"
     */
    private fun toolbarState() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id in arrayOf(R.id.addGameFragment)) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                supportActionBar?.setDisplayShowHomeEnabled(true)
                supportActionBar?.setTitle(R.string.add_game_fragment_title)
            } else {
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                supportActionBar?.setDisplayShowHomeEnabled(false)
                supportActionBar?.setTitle(R.string.app_name)
            }
        }
    }


}