package com.reza.mymvvm.util.common

import java.text.SimpleDateFormat
import java.util.*

object DateUtil {
    fun getTodayDateFormatted(pattern: String): String {
        val formatter = SimpleDateFormat(pattern, Locale.getDefault())
        val date = Calendar.getInstance().time
        return formatter.format(date)
    }

    fun getTimeGreetingMessage(): String = when (Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) {
        in 0..11 -> "Good Morning!"
        in 12..15 -> "Good Afternoon!"
        in 16..20 -> "Good Evening!"
        in 21..23 -> "Good Night!"
        else -> "Hello!"
    }
}