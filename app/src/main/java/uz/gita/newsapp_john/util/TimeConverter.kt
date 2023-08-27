package uz.gita.newsapp_john.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

fun convertServerTimestampToRelativeTime(timestamp: String): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    val date = dateFormat.parse(timestamp)

    val currentTime = Date()
    val diffInMillis = currentTime.time - date.time

    val diffInHours = TimeUnit.MILLISECONDS.toHours(diffInMillis)
    return "$diffInHours hours ago"
}