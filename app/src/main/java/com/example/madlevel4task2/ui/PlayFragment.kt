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
import java.util.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class PlayFragment : Fragment() {

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
    }
}