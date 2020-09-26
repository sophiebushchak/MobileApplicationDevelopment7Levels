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

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_delete_backlog -> {
            viewModel.clearBacklog()
            true
        } else -> {
            super.onOptionsItemSelected(item)
        }
    }

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
                val snackbar = Snackbar.make(rvBacklog,
                    getString(R.string.game_removed, gameToDelete.title),
                    Snackbar.LENGTH_SHORT)
                snackbar.setAction(R.string.undo_string, UndoGameDeleteListener(gameToDelete))
                snackbar.show()
            }
        }
        return ItemTouchHelper(callback)
    }

    private inner class UndoGameDeleteListener(val game: Game): View.OnClickListener {
        override fun onClick(v: View) {
            viewModel.insertGame(game)
        }
    }

}