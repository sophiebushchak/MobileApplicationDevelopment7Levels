package com.example.madlevel4task2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.madlevel4task2.model.Game
import com.example.madlevel4task2.model.GameResult
import com.example.madlevel4task2.model.Throw
import kotlinx.android.synthetic.main.fragment_play.*
import java.time.Instant
import java.time.LocalDateTime
import java.util.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class PlayFragment : Fragment() {
    private var currentGame: Game? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_play, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

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
    }

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

    private fun onClickThrow(thrown: Throw) {
        val opponentThrown = Throw.values().random()
        val result: GameResult = Game.getResult(thrown, opponentThrown)
        if (result != GameResult.DRAW) {
            tvGameResult.text = getString(R.string.tvResult, result.toString().toLowerCase(Locale.US))
        } else {
            tvGameResult.text = getString(R.string.tvResult, "drew")
        }
        ivResultPlayerThrow.setImageResource(Game.getThrowImage(thrown))
        ivResultOpponentThrow.setImageResource(Game.getThrowImage(opponentThrown))
        this.currentGame = Game(Date(), thrown, opponentThrown, result)
        setResultVisibility()
    }
}