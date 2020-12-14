package com.example.madlevel5task2.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.madlevel5task2.R
import com.example.madlevel5task2.vm.BacklogViewModel
import kotlinx.android.synthetic.main.fragment_add_game.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class AddGameFragment : Fragment() {
    private lateinit var navController: NavController
    private val viewModel: BacklogViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)

        return inflater.inflate(R.layout.fragment_add_game, container, false)
    }

    /**
     * Sets click listener on the fab to save the currently entered game and triggers [observeStatus]
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        fabSave.setOnClickListener {
            insertGame()
        }
        observeStatus()
    }

    /**
     * Sets the toolbar menu to be an empty menu.
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_add_game, menu)
    }

    /**
     * Sets what happens when the back arrow is pressed in the toolbar.
     */
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            navController.popBackStack()
        } else -> {
            super.onOptionsItemSelected(item)
        }
    }

    /**
     * Sets observers on the error and success data from the view model.
     */
    private fun observeStatus() {
        viewModel.error.observe(viewLifecycleOwner, Observer { message ->
            println(message)
            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
        })
        viewModel.success.observe(viewLifecycleOwner, Observer {
            println("Successfully created a game.")
            findNavController().popBackStack()
        })
    }

    /**
     * Inserts the game using the current values in the form fields.
     */
    private fun insertGame() {
        viewModel.insertGame(
            etTitle.text.toString(),
            etPlatforms.text.toString(),
            etReleaseDateDay.text.toString(),
            etReleaseDateYear.text.toString(),
            etReleaseDateMonth.text.toString()
        )
    }
}