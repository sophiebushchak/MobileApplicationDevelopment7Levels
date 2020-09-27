package com.example.madlevel4task2

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madlevel4task2.model.Game
import com.example.madlevel4task2.repository.GameHistoryRepository
import com.example.madlevel4task2.ui.GameHistoryAdapter
import kotlinx.android.synthetic.main.fragment_game_history.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class GameHistoryFragment : Fragment() {
    private val gameHistory = arrayListOf<Game>()
    private val gameHistoryAdapter = GameHistoryAdapter(gameHistory)
    private lateinit var gameHistoryRepository: GameHistoryRepository
    private val mainScope = CoroutineScope(Dispatchers.Main)
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var navController: NavController


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_game_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gameHistoryRepository = GameHistoryRepository(requireContext())
        navController = findNavController()
        getGameHistoryFromDatabase()
        initViews()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_history, menu)
    }

    /**
     * Does something based on the action clicked in the toolbar
     */
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_pop_backstack -> {
            navController.popBackStack()
            true;
        }
        R.id.action_delete_history -> {
            deleteGameHistory()
            true;
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    /**
     * Sets up the RecyclerView
     */
    private fun initViews() {
        viewManager = LinearLayoutManager(activity)
        rvGameHistory.addItemDecoration(
            DividerItemDecoration(
                activity,
                DividerItemDecoration.VERTICAL
            )
        )
        rvGameHistory.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = gameHistoryAdapter
        }
    }

    /**
     * Retrieves Game history from the database
     */
    private fun getGameHistoryFromDatabase() {
        mainScope.launch {
            val gameHistory = withContext(Dispatchers.IO) {
                gameHistoryRepository.getGameHistory()
            }
            this@GameHistoryFragment.gameHistory.clear()
            this@GameHistoryFragment.gameHistory.addAll(gameHistory)
            this@GameHistoryFragment.gameHistoryAdapter.notifyDataSetChanged()
        }
    }

    /**
     * Deletes all Games in the database
     */
    private fun deleteGameHistory() {
        mainScope.launch {
            withContext(Dispatchers.IO) {
                gameHistoryRepository.deleteGameHistory()
            }
            getGameHistoryFromDatabase()
        }
    }

}