package com.openclassrooms.arista.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.openclassrooms.arista.data.entity.UserDto
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) pour la gestion des exercices dans la base de données Room.
 *
 * Cette interface définit toutes les opérations CRUD (Create, Read, Update, Delete)
 * pour les exercices physiques de l'application.
 *
 * Elle utilise des coroutines et Flow pour des opérations asynchrones et réactives.
 *
 * Room génère automatiquement l'implémentation de cette interface au moment de la compilation.
 **/

@Dao
interface UserDtoDao {

    //Insère un nouvel utilisateur dans la base de données.
    @Insert
    suspend fun insertUser(user: UserDto): Long

    //Récupère tous les utilisateurs de la base de données.
    @Query("SELECT * FROM user")
    fun getAllUsers(): Flow<List<UserDto>>

    //Récupère un utilisateur spécifique par son identifiant.
    @Query("SELECT * FROM user WHERE id = :id")
    fun getUserById(id: Long): Flow<UserDto?>

    // Récupère un utilisateur par son adresse email.
    @Query("SELECT * FROM user WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): UserDto?

    //Met à jour les informations d'un utilisateur existant.
    @Update
    suspend fun updateUser(user: UserDto)

    // Supprime un utilisateur de la base de données.
    @Query("DELETE FROM user WHERE id = :id")
    suspend fun deleteUserById(id: Long)

}