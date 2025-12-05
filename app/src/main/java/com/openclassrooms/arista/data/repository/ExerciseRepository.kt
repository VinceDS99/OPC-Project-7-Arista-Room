package com.openclassrooms.arista.data.repository

import com.openclassrooms.arista.data.dao.ExerciseDtoDao
import com.openclassrooms.arista.data.entity.ExerciseDto
import com.openclassrooms.arista.domain.model.Exercise
import kotlinx.coroutines.flow.first
import java.time.Instant
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExerciseRepository @Inject constructor(private val exerciseDao: ExerciseDtoDao) {


    // Récupérer tous les exercices
    suspend fun getAllExercises(): List<Exercise> {
        return exerciseDao.getAllExercises()
            .first() // Collecte la première émission du Flow
            .map { it.toDomain() } // Conversion DTO -> Domaine
    }

    // Ajouter un exercice
    suspend fun addExercise(exercise: Exercise) {
        exerciseDao.insertExercise(exercise.toDto())
    }

    // Supprimer un exercice
    suspend fun deleteExercise(exercise: Exercise) {
        exercise.id?.let {
            exerciseDao.deleteExerciseById(it)
        }
    }

    // Conversion Exercise -> ExerciseDto
    private fun Exercise.toDto(): ExerciseDto = ExerciseDto(
        id = this.id ?: 0,
        userId = this.userId,
        startTime = this.startTime.toEpochMilli(),
        duration = this.duration,
        category = this.category,
        intensity = this.intensity
    )

    // Conversion ExerciseDto -> Exercise
    private fun ExerciseDto.toDomain(): Exercise = Exercise(
        id = this.id,
        userId = this.userId,
        startTime = Instant.ofEpochMilli(this.startTime),
        duration = this.duration,
        category = this.category,
        intensity = this.intensity
    )


}
