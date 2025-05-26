package ru.mareanexx.common.utils

import java.time.format.DateTimeFormatter
import java.util.Locale

object DateFormatter {
    val profileCreationDateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.ENGLISH)
    val articleCreationDateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH)
}