package com.openclassrooms.arista.domain.usecase

import com.openclassrooms.arista.data.repository.ExerciseRepository
import com.openclassrooms.arista.domain.model.Exercise
import javax.inject.Inject

class AddNewExerciseUseCase @Inject constructor(
    private val repository: ExerciseRepository
) {
    suspend fun execute(exercise: Exercise) = repository.addExercise(exercise)
}
