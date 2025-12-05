package com.openclassrooms.arista.data.repository

import com.openclassrooms.arista.data.dao.SleepDtoDao
import com.openclassrooms.arista.data.entity.SleepDto
import com.openclassrooms.arista.domain.model.Sleep
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton
import java.time.Instant

@Singleton
class SleepRepository @Inject constructor(private val sleepDao: SleepDtoDao) {


    // Récupérer tous les sleeps
    suspend fun getAllSleeps(): List<Sleep> {
        return sleepDao.getAllSleeps()
            .first() // Collecte la première émission du Flow
            .map { it.toDomain() } // Conversion DTO -> Domaine
    }

    // Ajouter un sleep
    suspend fun addSleep(sleep: Sleep) {
        sleepDao.insertSleep(sleep.toDto())
    }

    // Supprimer un sleep
    suspend fun deleteSleep(sleep: Sleep) {
        sleep.id?.let {
            sleepDao.deleteSleepById(it)
        }
    }

    // Conversion Sleep -> SleepDto
    private fun Sleep.toDto(): SleepDto = SleepDto(
        id = this.id ?: 0,
        userId = this.userId,
        startTime = this.startTime.toEpochMilli(),
        duration = this.duration,
        quality = this.quality
    )

    // Conversion SleepDto -> Sleep
    private fun SleepDto.toDomain(): Sleep = Sleep(
        id = this.id,
        userId = this.userId,
        startTime = Instant.ofEpochMilli(this.startTime),
        duration = this.duration,
        quality = this.quality
    )


}
