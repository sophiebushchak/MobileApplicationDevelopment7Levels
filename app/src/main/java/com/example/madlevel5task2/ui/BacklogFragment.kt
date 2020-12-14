package com.example.madlevel5task2.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madlevel5task2.R
import com.example.madlevel5task2.model.Game
import com.example.madlevel5task2.vm.BacklogViewModel
import kotlinx.android.synthetic.main.fragment_backlog.*
import java.util.*
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class BacklogFragment : Fragment() {
    private val backlog = arrayListOf<Game>()
    private val backlogAdapter = BacklogAdapter(backlog)
    private val viewModel: BacklogViewModel by viewModels()
    private lateinit var navController: NavController

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_backlog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        initViews()
        observeBacklogChanges()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
    }

    /**
     * Sets the action_delete_backlog in the menu to store the current backlog in a backup,
     * then removes the current backup and displays a Snackbar menu to give the user a chance to undo.
     */
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_delete_backlog -> {
            val backlogBackup = arrayListOf<Game>()
            backlogBackup.addAll(backlog)
            viewModel.clearBacklog()
            val snackbar = Snackbar.make(rvBacklog,
                R.string.backlog_cleared,
                Snackbar.LENGTH_SHORT)
            snackbar.setAction(R.string.undo_string, UndoClearBacklog(backlogBackup))
            snackbar.show()
            true
        } else -> {
            super.onOptionsItemSelected(item)
        }
    }

    /**
     * Sets up the RecyclerView and adds a click listener to navigate to the AddGameFragment on the
     * fab.
     */
    private fun initViews() {
        rvBacklog.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rvBacklog.adapter = backlogAdapter
        rvBacklog.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        createItemTouchHelper().attachToRecyclerView(rvBacklog)
        fabAddGame.setOnClickListener {
            navController.navigate(
                R.id.action_backlogFragment_to_addGameFragment
            )
        }
    }

    /**
     * Listens to changes in the LiveData of the view model.
     */
    private fun observeBacklogChanges() {
        viewModel.backlog.observe(viewLifecycleOwner, Observer {
            backlog ->
            this@BacklogFragment.backlog.clear()
            this@BacklogFragment.backlog.addAll(backlog)
            backlogAdapter.notifyDataSetChanged()
        })
    }

    /**
     * Create a touch helper to recognize when a user swipes an item from a recycler view.
     * An ItemTouchHelper enables touch behavior (like swipe and move) on each ViewHolder,
     * and uses callbacks to signal when a user is performing these actions.
     */
    private fun createItemTouchHelper(): ItemTouchHelper {

        // Callback which is used to create the ItemTouch helper. Only enables left swipe.
        // Use ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) to also enable right swipe.
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            // Enables or Disables the ability to move items up and down.
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            // Callback triggered when a user swiped an item.
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val gameToDelete = backlog[position]
                viewModel.deleteGame(gameToDelete)
                //Gives user a chance to reverse action.
                val snackbar = Snackbar.make(rvBacklog,
                    getString(R.string.game_removed, gameToDelete.title),
                    Snackbar.LENGTH_SHORT)
                snackbar.setAction(R.string.undo_string, UndoGameDeleteListener(gameToDelete))
                snackbar.show()
            }
        }
        return ItemTouchHelper(callback)
    }

    /**
     * Listener where a game is passed.
     * Its purpose is to insert the game back into the backlog after a deletion.
     */
    private inner class UndoGameDeleteListener(val game: Game): View.OnClickListener {
        override fun onClick(v: View) {
            viewModel.insertGame(game)
        }
    }

    /**
     * Listener where a list of games is passed.
     * Its purpose is to insert the backlog backup that was made before clearing the backlog back in.
     */
    private inner class UndoClearBacklog(val backlogToRestore: ArrayList<Game>): View.OnClickListener {
        override fun onClick(v: View) {
            for (game in backlogToRestore) {
                viewModel.insertGame(game)
            }
        }
    }

}