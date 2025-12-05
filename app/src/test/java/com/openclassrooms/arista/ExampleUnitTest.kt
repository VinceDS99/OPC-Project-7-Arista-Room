import com.openclassrooms.arista.data.repository.ExerciseRepository
import com.openclassrooms.arista.domain.model.Exercise
import com.openclassrooms.arista.domain.model.ExerciseCategory
import com.openclassrooms.arista.domain.model.User
import com.openclassrooms.arista.domain.usecase.GetAllExercisesUseCase
import com.openclassrooms.arista.domain.usecase.GetUserUsecase
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
class ExampleUnitTest {


    @Mock
    private lateinit var exerciseRepository: ExerciseRepository

    @Mock
    private lateinit var getUserUsecase: GetUserUsecase

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
        val fakeExercise1: Exercise = Exercise(
            startTime = Instant.now(),
            duration = 30,
            category = ExerciseCategory.Running,
            intensity = 5
        )
        val fakeExercise2: Exercise = Exercise(
            startTime = Instant.now().plusSeconds(3600), // +1 heure
            duration = 45,
            category = ExerciseCategory.Riding,
            intensity = 7
        )
        val fakeExercises: List<Exercise> = listOf(fakeExercise1, fakeExercise2)
        Mockito.`when`(exerciseRepository.getAllExercises()).thenReturn(fakeExercises)

        // Act
        val result: List<Exercise> = getAllExercisesUseCase.execute()

        // Assert
        assertEquals(fakeExercises, result)
    }

    @Test
    fun `when repository returns empty list, use case should return empty list`() = runBlocking {
        // Arrange
        Mockito.`when`(exerciseRepository.getAllExercises()).thenReturn(emptyList())

        // Act
        val result: List<Exercise> = getAllExercisesUseCase.execute()

        // Assert
        assertTrue(result.isEmpty())
    }

    @Test
    fun `when getUserUsecase called, should return user`() = runBlocking {
        // Arrange
        val fakeUser: User = User(
            id = 1L,
            name = "John Doe",
            email = "john@example.com",
            password = "secret"
        )
        val userId: Long = 1L
        Mockito.`when`(getUserUsecase.execute(userId)).thenReturn(fakeUser)

        // Act
        val result: User? = getUserUsecase.execute(userId)

        // Assert
        assertEquals(fakeUser, result)
    }


}
