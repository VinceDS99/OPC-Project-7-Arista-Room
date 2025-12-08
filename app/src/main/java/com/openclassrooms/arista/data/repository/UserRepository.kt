package com.openclassrooms.arista.data.repository

import com.openclassrooms.arista.data.dao.UserDtoDao
import com.openclassrooms.arista.domain.model.User
import kotlinx.coroutines.flow.first

/**
 * Repository pour la gestion des utilisateurs.
 *
 * Ce Repository implémente le pattern Repository pour abstraire l'accès aux
 * données utilisateur. Il fait le pont entre la couche de données (Room) et
 * la couche de domaine (Use Cases).
 *
 * @param userDao DAO Room pour accéder à la table user
 */

class UserRepository(private val userDao: UserDtoDao) {

    //Récupère tous les utilisateurs de la base de données.
    suspend fun getAllUsers(): List<User> {
        return userDao.getAllUsers()
            .first()
            .map { User.fromDto(it) }
    }

    //Récupère un utilisateur spécifique par son identifiant.
    suspend fun getUserById(id: Long): User? {
        return userDao.getUserById(id).first()?.let { User.fromDto(it) }
    }

    //Récupère un utilisateur par son adresse email.
    suspend fun getUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email)?.let { User.fromDto(it) }
    }

    //Ajoute un nouvel utilisateur dans la base de données.
    suspend fun addUser(user: User): Long {
        return userDao.insertUser(user.toDto())
    }

    //Met à jour les informations d'un utilisateur existant.
    suspend fun updateUser(user: User) {
        userDao.updateUser(user.toDto())
    }

    //Supprime un utilisateur de la base de données.
    suspend fun deleteUser(user: User) {
        user.id?.let {
            userDao.deleteUserById(it)
        }
    }
}