package com.openclassrooms.arista.domain.usecase

import com.openclassrooms.arista.data.repository.UserRepository
import com.openclassrooms.arista.domain.model.User
import javax.inject.Inject

class GetUserUsecase @Inject constructor(private val userRepository: UserRepository) {


    // Récupère un utilisateur par son id
    suspend fun execute(userId: Long): User? {
        return userRepository.getUserById(userId)
    }



}
