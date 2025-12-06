package com.openclassrooms.arista

import com.openclassrooms.arista.data.repository.ExerciseRepository
import com.openclassrooms.arista.domain.model.Exercise
import com.openclassrooms.arista.domain.usecase.AddNewExerciseUseCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.time.Instant

@RunWith(JUnit4::class)
class AddNewExerciseUseCaseTest {

    @Mock
    private lateinit var exerciseRepository: ExerciseRepository

    private lateinit var addNewExerciseUseCase: AddNewExerciseUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        addNewExerciseUseCase = AddNewExerciseUseCase(exerciseRepository)
    }

    @After
    fun tearDown() {
        Mockito.framework().clearInlineMocks()
    }

    @Test
    fun `when adding exercise, repository addExercise should be called`() = runBlocking {
        // Arrange
        val exercise = Exercise(
            id = null,
            userId = 1L,
            startTime = Instant.now(),
            duration = 30,
            category = "Running",
            intensity = 5
        )

        // Act
        addNewExerciseUseCase.execute(exercise)

        // Assert
        Mockito.verify(exerciseRepository, Mockito.times(1)).addExercise(exercise)
    }
}