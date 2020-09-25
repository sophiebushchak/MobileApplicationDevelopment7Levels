package com.example.madlevel5example.repository

import android.content.Context
import com.example.madlevel5example.database.ReminderRoomDatabase
import com.example.madlevel5example.dao.ReminderDao
import com.example.madlevel5example.model.Reminder

public class ReminderRepository(context: Context) {

    private var reminderDao: ReminderDao

    init {
        val reminderRoomDatabase =
            ReminderRoomDatabase.getDatabase(
                context
            )
        reminderDao = reminderRoomDatabase!!.reminderDao()
    }

    suspend fun getAllReminders(): List<Reminder> {
        return reminderDao.getAllReminders()
    }

    suspend fun insertReminder(reminder: Reminder) {
        reminderDao.insertReminder(reminder)
    }

    suspend fun deleteReminder(reminder: Reminder) {
        reminderDao.deleteReminder(reminder)
    }

    suspend fun updateReminder(reminder: Reminder) {
        reminderDao.updateReminder(reminder)
    }

}
