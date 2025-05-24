package ru.mareanexx.data.profile.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ru.mareanexx.data.converters.LocalDateConverter
import ru.mareanexx.data.converters.OffsetDateTimeConverter
import java.time.LocalDate
import java.time.OffsetDateTime
import java.util.UUID

@Entity(tableName = "profile")
@TypeConverters(
    value = [
        OffsetDateTimeConverter::class,
        LocalDateConverter::class
    ]
)
data class ProfileEntity(
    @PrimaryKey val id: Int,
    val email: String,
    val username: String,
    val avatar: String?,
    val birthdate: LocalDate?,
    val bio: String?,

    @ColumnInfo(name = "user_uuid")
    val userUuid: UUID,

    @ColumnInfo(name = "created_at")
    val createdAt: OffsetDateTime
)