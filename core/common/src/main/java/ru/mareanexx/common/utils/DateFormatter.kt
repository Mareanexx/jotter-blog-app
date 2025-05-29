package ru.mareanexx.common.utils

import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.ceil

object DateFormatter {
    val profileCreationDateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.ENGLISH)
    val articleDateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH)

    fun secondsToFormattedTime(seconds: Int) = when(seconds) {
        in 0..<60 -> "less than a min"
        in 61..<3600 -> "${seconds / 60} min read"
        else -> "${seconds / 3600} h ${ceil(seconds % 3600 / 60.0).toInt()} min read"
    }
}