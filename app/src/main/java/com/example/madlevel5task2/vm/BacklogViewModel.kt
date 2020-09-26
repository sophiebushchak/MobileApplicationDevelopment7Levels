package com.example.madlevel5task2.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.madlevel5task2.database.BacklogRepository
import com.example.madlevel5task2.model.Game
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class BacklogViewModel(application: Application) : AndroidViewModel(application) {
    private val mainScope = CoroutineScope(Dispatchers.Main)
    private val ioScope = CoroutineScope(Dispatchers.IO)
    private val backlogRepository = BacklogRepository(application.applicationContext)
    val backlog: LiveData<List<Game>> = backlogRepository.getBacklog()

    val error = MutableLiveData<String>()
    val success = MutableLiveData<Boolean>()

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

    fun deleteGame(game: Game) {
        ioScope.launch {
            backlogRepository.deleteGameFromBacklog(game)
        }
    }

    fun clearBacklog() {
        ioScope.launch {
            backlogRepository.deleteBacklog()
        }
    }

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

    private fun returnDateFromFields(releaseDateDay: String, releaseDateYear: String,
                                   releaseDateMonth: String): Date? {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val dateString = "$releaseDateYear-$releaseDateMonth-$releaseDateDay"
        return sdf.parse(dateString)
    }
}