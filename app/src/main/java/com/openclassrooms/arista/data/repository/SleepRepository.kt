package com.openclassrooms.arista.data.repository

import com.openclassrooms.arista.data.dao.SleepDtoDao
import com.openclassrooms.arista.data.entity.SleepDto
import com.openclassrooms.arista.domain.model.Sleep
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton
import java.time.Instant

/**
 * Repository pour la gestion des cycles de sommeil.
 *
 * Ce Repository applique le pattern Repository pour abstraire l'accès aux données
 * de sommeil. Il sert d'interface entre :
 * - La couche de données (Room/DAO) qui persiste les données
 * - La couche de domaine (Use Cases) qui contient la logique métier
 *
 * @param sleepDao DAO Room pour accéder à la table sleep, injecté par Hilt
 */

@Singleton
class SleepRepository @Inject constructor(
    private val sleepDao: SleepDtoDao
) {

    //Récupère tous les cycles de sommeil depuis la base de données.
    suspend fun getAllSleeps(): List<Sleep> {
        return sleepDao.getAllSleeps()
            .first() // Attend la première émission du Flow
            .map { it.toDomain() } // Transforme List<SleepDto> en List<Sleep>
    }

    //Ajoute un nouveau cycle de sommeil dans la base de données.
    suspend fun addSleep(sleep: Sleep) {
        sleepDao.insertSleep(sleep.toDto())
    }

    //Supprime un cycle de sommeil de la base de données.
    suspend fun deleteSleep(sleep: Sleep) {
        sleep.id?.let {
            sleepDao.deleteSleepById(it)
        }
    }

    //Convertit un modèle du domaine (Sleep) en DTO de base de données (SleepDto).
    private fun Sleep.toDto(): SleepDto = SleepDto(
        id = this.id ?: 0,  // 0 = "génère un nouvel ID" pour Room
        userId = this.userId,
        startTime = this.startTime.toEpochMilli(),  // Instant → Long
        duration = this.duration,
        quality = this.quality
    )

    //Convertit un DTO de base de données (SleepDto) en modèle du domaine (Sleep).
    private fun SleepDto.toDomain(): Sleep = Sleep(
        id = this.id,  // Long → Long? (conserve l'ID existant)
        userId = this.userId,
        startTime = Instant.ofEpochMilli(this.startTime),  // Long → Instant
        duration = this.duration,
        quality = this.quality
    )

}