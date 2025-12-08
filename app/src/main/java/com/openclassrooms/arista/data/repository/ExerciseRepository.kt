package com.openclassrooms.arista.data.repository

import com.openclassrooms.arista.data.dao.ExerciseDtoDao
import com.openclassrooms.arista.data.entity.ExerciseDto
import com.openclassrooms.arista.domain.model.Exercise
import kotlinx.coroutines.flow.first
import java.time.Instant
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository pour la gestion des exercices physiques.
 *
 * Ce Repository fait le pont entre la couche de données (Room/DAO) et la couche
 * de domaine (Use Cases).
 *
 * (injectée par Hilt). Cela garantit la cohérence des données et optimise les ressources.
 *
 * @param exerciseDao DAO Room injecté par Hilt pour accéder à la table exercise
 */

@Singleton
class ExerciseRepository @Inject constructor(
    private val exerciseDao: ExerciseDtoDao
) {

    //Récupère tous les exercices depuis la base de données
    suspend fun getAllExercises(): List<Exercise> {
        return exerciseDao.getAllExercises()
            .first() // Collecte la première émission du Flow (snapshot actuel)
            .map { it.toDomain() } // Conversion DTO -> Domaine pour chaque exercice
    }

    //Ajoute un nouvel exercice dans la base de données.
    suspend fun addExercise(exercise: Exercise) {
        exerciseDao.insertExercise(exercise.toDto())
    }

    //Supprime un exercice de la base de données.
    suspend fun deleteExercise(exercise: Exercise) {
        exercise.id?.let {
            exerciseDao.deleteExerciseById(it)
        }
    }

    //Convertit un modèle du domaine (Exercise) en DTO de base de données (ExerciseDto)
    private fun Exercise.toDto(): ExerciseDto = ExerciseDto(
        id = this.id ?: 0,
        userId = this.userId,
        startTime = this.startTime.toEpochMilli(),
        duration = this.duration,
        category = this.category,
        intensity = this.intensity
    )

    //Convertit un DTO de base de données (ExerciseDto) en modèle du domaine (Exercise)
    private fun ExerciseDto.toDomain(): Exercise = Exercise(
        id = this.id,
        userId = this.userId,
        startTime = Instant.ofEpochMilli(this.startTime),
        duration = this.duration,
        category = this.category,
        intensity = this.intensity
    )
}