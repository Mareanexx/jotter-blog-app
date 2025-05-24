package ru.mareanexx.data.converters

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

class LocalDateConverter {
    @TypeConverter
    fun fromTimestamp(value: Long): LocalDate? {
        return if (value < 0) null
        else Instant.ofEpochMilli(value)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDate?): Long {
        return date?.run {
            atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()
                .toEpochMilli()
        } ?: -1L
    }
}