package com.openclassrooms.arista.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Index

@Entity(
    tableName = "exercise",
    foreignKeys = [
        ForeignKey(
            entity = UserDto::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["user_id"])]
)

data class ExerciseDto(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0,

    @ColumnInfo(name = "user_id")
    var userId: Long,

    @ColumnInfo(name = "start_time")
    var startTime: Long,

    @ColumnInfo(name = "duration")
    var duration: Int,

    @ColumnInfo(name = "category")
    var category: String,

    @ColumnInfo(name = "intensity")
    var intensity: Int
)