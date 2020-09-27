package com.example.madlevel5task2.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.madlevel5task2.database.BacklogRepository
import com.example.madlevel5task2.model.Game
import com.example.madlevel5task2.model.DateConstants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class BacklogViewModel(application: Application) : AndroidViewModel(application) {
    private val ioScope = CoroutineScope(Dispatchers.IO)
    private val backlogRepository = BacklogRepository(application.applicationContext)
    val backlog: LiveData<List<Game>> = backlogRepository.getBacklog()

    val error = MutableLiveData<String>()
    val success = MutableLiveData<Boolean>()

    /**
     * Inserts game through String values from the textfields.
     * It first gets a data from the [returnDateFromFields] method.
     * Then it passes this date and the title and platform to see if the entered data should be used
     * to enter a game into the database.
     */
    fun insertGame(title: String, platform: String, releaseDateDay: String,
                   releaseDateYear: String, releaseDateMonth: String) {
        val date: Date? = returnDateFromFields(releaseDateDay, releaseDateYear, releaseDateMonth)
        if (areFieldsValid(title, platform, date)) {
            ioScope.launch {
                backlogRepository.insertGame(Game(title, platform, date!!))
            }
            success.value = true
        }
    }

    /**
     * Alternative [insertGame] method to add a game from an object.
     */
    fun insertGame(game: Game) {
        ioScope.launch {
            backlogRepository.insertGame(game)
        }
    }

    /**
     * Deletes a game from the database.
     */
    fun deleteGame(game: Game) {
        ioScope.launch {
            backlogRepository.deleteGameFromBacklog(game)
        }
    }

    /**
     * Clears the backlog of games.
     */
    fun clearBacklog() {
        ioScope.launch {
            backlogRepository.deleteBacklog()
        }
    }

    /**
     * Checks if 3 values are valid so it can safely be used for a Game object.
     */
    private fun areFieldsValid(title: String, platform: String, date: Date?): Boolean {
        return when {
            title.isBlank() -> {
                error.value = "Title must not be empty."
                false
            }
            platform.isBlank() -> {
                error.value = "Platform must not be empty."
                false
            }
            date == null -> {
                error.value = "Incorrect date format entered."
                false
            } else -> true
        }
    }

    /**
     * Returns either null or a Date object from 3 string values.
     * It first checks if the entered values fall within the allowed date ranges.
     * Then it tries to make a Date out of this.
     * Catching the NumberFormatException is for potentially empty Strings.
     * Catching the ParseException is for dateStrings that don't result in any Date.
     */
    private fun returnDateFromFields(releaseDateDay: String, releaseDateYear: String,
                                   releaseDateMonth: String): Date? {
        try {
            if (releaseDateDay.toInt() < DateConstants.DAY_MIN ||
                releaseDateDay.toInt() > DateConstants.DAY_MAX ||
                releaseDateMonth.toInt() < DateConstants.MONTH_MIN ||
                releaseDateMonth.toInt() > DateConstants.MONTH_MAX ||
                releaseDateYear.toInt() < DateConstants.YEAR_MIN ||
                releaseDateYear.toInt() > DateConstants.YEAR_MAX
            ) {
                return null
            }
        } catch (e: java.lang.NumberFormatException) {
            return null
        }
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val dateString = "$releaseDateYear-$releaseDateMonth-$releaseDateDay"
        try {
            return sdf.parse(dateString)
        } catch (e: ParseException) {
            return null
        }

    }
}