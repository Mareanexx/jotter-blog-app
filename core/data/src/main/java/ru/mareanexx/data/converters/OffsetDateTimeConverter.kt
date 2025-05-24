package ru.mareanexx.data.converters

import androidx.room.TypeConverter
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class OffsetDateTimeConverter {
    private val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    @TypeConverter
    fun fromString(value: String?): OffsetDateTime? {
        return value?.let {
            OffsetDateTime.parse(it, formatter)
        }
    }

    @TypeConverter
    fun toString(dateTime: OffsetDateTime?): String? {
        return dateTime?.format(formatter)
    }
}