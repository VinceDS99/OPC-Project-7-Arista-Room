package com.openclassrooms.arista

import com.openclassrooms.arista.data.repository.ExerciseRepository
import com.openclassrooms.arista.domain.model.Exercise
import com.openclassrooms.arista.domain.usecase.GetAllExercisesUseCase
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
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@RunWith(JUnit4::class)
class GetAllExercisesUseCaseTest {

    @Mock
    private lateinit var exerciseRepository: ExerciseRepository

    private lateinit var getAllExercisesUseCase: GetAllExercisesUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        getAllExercisesUseCase = GetAllExercisesUseCase(exerciseRepository)
    }

    @After
    fun tearDown() {
        Mockito.framework().clearInlineMocks()
    }

    @Test
    fun `when repository returns exercises, use case should return them`() = runBlocking {
        // Arrange
        val fakeExercises = listOf(
            Exercise(
                id = 1L,
                userId = 1L,
                startTime = Instant.now(),
                duration = 30,
                category = "Running",
                intensity = 5
            ),
            Exercise(
                id = 2L,
                userId = 1L,
                startTime = Instant.now().plusSeconds(3600),
                duration = 45,
                category = "Riding",
                intensity = 7
            )
        )
        Mockito.`when`(exerciseRepository.getAllExercises()).thenReturn(fakeExercises)

        // Act
        val result = getAllExercisesUseCase.execute()

        // Assert
        assertEquals(fakeExercises, result)
        assertEquals(2, result.size)
    }

    @Test
    fun `when repository returns empty list, use case should return empty list`() = runBlocking {
        // Arrange
        Mockito.`when`(exerciseRepository.getAllExercises()).thenReturn(emptyList())

        // Act
        val result = getAllExercisesUseCase.execute()

        // Assert
        assertTrue(result.isEmpty())
    }
}