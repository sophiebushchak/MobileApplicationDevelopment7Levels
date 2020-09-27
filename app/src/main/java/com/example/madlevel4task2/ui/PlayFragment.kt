package com.example.madlevel4task2

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.madlevel4task2.model.Game
import com.example.madlevel4task2.model.GameResult
import com.example.madlevel4task2.model.Throw
import com.example.madlevel4task2.repository.GameHistoryRepository
import kotlinx.android.synthetic.main.fragment_play.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class PlayFragment : Fragment() {
    private var currentGame: Game? = null
    private lateinit var gameHistoryRepository: GameHistoryRepository
    private val mainScope = CoroutineScope(Dispatchers.Main)
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_play, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gameHistoryRepository = GameHistoryRepository(requireContext())
        navController = findNavController()
        initViews()
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
    }

    /**
     * Sets what happens when items in the toolbar menu are pressed.
     * To be tidy, the last game of Rock Paper Scissors played is removed before leaving the fragment.
     */
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_goto_history -> {
            this.currentGame = null
            setResultVisibility()
            navController.navigate(R.id.action_playFragment_to_gameHistoryFragment)
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    /**
     * Sets click listeners on the 3 buttons for playing the game and sets the game results to invisible.
     * Also retrieves the statistics.
     */
    private fun initViews() {
        setResultVisibility()
        ivThrowPaper.setOnClickListener {
            onClickThrow(Throw.PAPER)
        }
        ivThrowRock.setOnClickListener {
            onClickThrow(Throw.ROCK)

        }
        ivThrowScissors.setOnClickListener {
            onClickThrow(Throw.SCISSORS)
        }
        updateStatisticsView()
    }

    /**
     * To not display empty images and text before a game is played, sets all the views related to the
     * game result to invisible when there is no game that was played.
     */
    private fun setResultVisibility() {
        if (this.currentGame != null) {
            tvGameResult.visibility = View.VISIBLE
            tvVersus.visibility = View.VISIBLE
            tvResultLabelYou.visibility = View.VISIBLE
            tvResultLabelOpponent.visibility = View.VISIBLE
            ivResultPlayerThrow.visibility = View.VISIBLE
            ivResultOpponentThrow.visibility = View.VISIBLE
        } else {
            tvGameResult.visibility = View.INVISIBLE
            tvVersus.visibility = View.INVISIBLE
            tvResultLabelYou.visibility = View.INVISIBLE
            tvResultLabelOpponent.visibility = View.INVISIBLE
            ivResultPlayerThrow.visibility = View.INVISIBLE
            ivResultOpponentThrow.visibility = View.INVISIBLE
        }
    }

    /**
     * Updates the views related to the game result with appropriate resources.
     */
    private fun updateResultViews(game: Game?) {
        if (game != null) {
            if (game.result != GameResult.DRAW) {
                tvGameResult.text =
                    getString(R.string.tvResult, game.result.toString().toLowerCase(Locale.US))
            } else {
                tvGameResult.text = getString(R.string.tvResult, "drew")
            }
            ivResultPlayerThrow.setImageResource(Game.getThrowImage(game.playerThrow))
            ivResultOpponentThrow.setImageResource(Game.getThrowImage(game.opponentThrow))
        }
    }

    /**
     * Retrieves the statistics and updates the related view.
      */
    private fun updateStatisticsView() {
        mainScope.launch {
            var wins = 0
            var losses = 0
            var draws = 0
            withContext(Dispatchers.IO) {
                wins = gameHistoryRepository.countAResult(GameResult.WIN)
                losses = gameHistoryRepository.countAResult(GameResult.LOSE)
                draws = gameHistoryRepository.countAResult(GameResult.DRAW)
            }
            tvStatistics.text = getString(R.string.tvStatistics, wins, draws, losses)
        }
    }

    /**
     * Click listener for the game buttons.
     */
    private fun onClickThrow(thrown: Throw) {
        val opponentThrown = Throw.values().random()
        currentGame = Game(Date(), thrown, opponentThrown, Game.getResult(thrown, opponentThrown))
        setResultVisibility()
        mainScope.launch {
            withContext(Dispatchers.IO) {
                gameHistoryRepository.insertIntoGameHistory(currentGame!!)
                updateStatisticsView()
            }
        }
        updateResultViews(currentGame)
    }
}