package com.openclassrooms.arista.domain.model

import com.openclassrooms.arista.data.entity.ExerciseDto
import java.time.Instant

data class Exercise(
    val id: Long? = null,
    val userId: Long,
    val startTime: Instant,
    val duration: Int,
    val category: String,
    val intensity: Int
) {
    fun toDto() = ExerciseDto(
        id = this.id ?: 0,
        userId = this.userId,
        startTime = this.startTime.toEpochMilli(), // Conversion Instant -> Long
        duration = this.duration,
        category = this.category,
        intensity = this.intensity
    )

    companion object {
        fun fromDto(dto: ExerciseDto) = Exercise(
            id = dto.id,
            userId = dto.userId,
            startTime = Instant.ofEpochMilli(dto.startTime), // Conversion Long -> Instant
            duration = dto.duration,
            category = dto.category,
            intensity = dto.intensity
        )
    }


}
