package com.example.madlevel4task2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gameHistoryRepository = GameHistoryRepository(requireContext())
        getGameHistoryFromDatabase()
        initViews()
    }

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

}