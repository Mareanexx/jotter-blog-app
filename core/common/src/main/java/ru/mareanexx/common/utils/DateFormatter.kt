package ru.mareanexx.common.utils

import java.time.format.DateTimeFormatter
import java.util.Locale

object DateFormatter {
    val profileCreationDateFormatter = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.ENGLISH)
}