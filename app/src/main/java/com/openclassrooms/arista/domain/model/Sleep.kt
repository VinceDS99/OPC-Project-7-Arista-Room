package com.openclassrooms.arista.domain.model

import com.openclassrooms.arista.data.entity.SleepDto
import java.time.Instant

data class Sleep(
    val id: Long? = null,
    val userId: Long,
    val startTime: Instant,
    val duration: Int,
    val quality: Int
) {
    fun toDto() = SleepDto(
        id = this.id ?: 0,
        userId = this.userId,
        startTime = this.startTime.toEpochMilli(), // Conversion Instant -> Long
        duration = this.duration,
        quality = this.quality
    )

    companion object {
        fun fromDto(dto: SleepDto) = Sleep(
            id = dto.id,
            userId = dto.userId,
            startTime = Instant.ofEpochMilli(dto.startTime), // Conversion Long -> Instant
            duration = dto.duration,
            quality = dto.quality
        )
    }


}
