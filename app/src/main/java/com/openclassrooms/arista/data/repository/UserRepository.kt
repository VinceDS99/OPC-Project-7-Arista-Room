package com.openclassrooms.arista.data.repository

import com.openclassrooms.arista.data.dao.UserDtoDao
import com.openclassrooms.arista.domain.model.User
import kotlinx.coroutines.flow.first

class UserRepository(private val userDao: UserDtoDao) {

    // Get all users
    suspend fun getAllUsers(): List<User> {
        return userDao.getAllUsers()
            .first()
            .map { User.fromDto(it) }
    }

    // Get user by ID
    suspend fun getUserById(id: Long): User? {
        return userDao.getUserById(id).first()?.let { User.fromDto(it) }
    }

    // Get user by email
    suspend fun getUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email)?.let { User.fromDto(it) }
    }

    // Add a new user
    suspend fun addUser(user: User): Long {
        return userDao.insertUser(user.toDto())
    }

    // Update user
    suspend fun updateUser(user: User) {
        userDao.updateUser(user.toDto())
    }

    // Delete user
    suspend fun deleteUser(user: User) {
        user.id?.let {
            userDao.deleteUserById(it)
        }
    }
}
