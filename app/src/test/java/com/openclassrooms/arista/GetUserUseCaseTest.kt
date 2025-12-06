package com.openclassrooms.arista

import com.openclassrooms.arista.data.repository.UserRepository
import com.openclassrooms.arista.domain.model.User
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
import kotlin.test.assertEquals
import kotlin.test.assertNull

@RunWith(JUnit4::class)
class GetUserUseCaseTest {

    @Mock
    private lateinit var userRepository: UserRepository

    private lateinit var getUserUseCase: GetUserUsecase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        getUserUseCase = GetUserUsecase(userRepository)
    }

    @After
    fun tearDown() {
        Mockito.framework().clearInlineMocks()
    }

    @Test
    fun `when repository returns user, use case should return user`() = runBlocking {
        // Arrange
        val fakeUser = User(
            id = 1L,
            name = "John Doe",
            email = "john@example.com",
            password = "password123"
        )
        Mockito.`when`(userRepository.getUserById(1L)).thenReturn(fakeUser)

        // Act
        val result = getUserUseCase.execute(1L)

        // Assert
        assertEquals(fakeUser, result)
        assertEquals("John Doe", result?.name)
    }

    @Test
    fun `when repository returns null, use case should return null`() = runBlocking {
        // Arrange
        Mockito.`when`(userRepository.getUserById(999L)).thenReturn(null)

        // Act
        val result = getUserUseCase.execute(999L)

        // Assert
        assertNull(result)
    }
}