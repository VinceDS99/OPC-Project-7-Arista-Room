package com.openclassrooms.arista.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.openclassrooms.arista.data.entity.SleepDto
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
interface SleepDtoDao {


    //Insère un nouveau cycle de sommeil dans la base de données.
    @Insert
    suspend fun insertSleep(sleep: SleepDto): Long


    //Récupère tous les cycles de sommeil enregistrés dans la base de données.
    @Query("SELECT * FROM sleep")
    fun getAllSleeps(): Flow<List<SleepDto>>


    //Récupère tous les cycles de sommeil d'un utilisateur spécifique.
    @Query("SELECT * FROM sleep WHERE user_id = :userId")
    fun getSleepsByUserId(userId: Long): Flow<List<SleepDto>>

    //Supprime un cycle de sommeil spécifique de la base de données.
    @Query("DELETE FROM sleep WHERE id = :id")
    suspend fun deleteSleepById(id: Long)

}