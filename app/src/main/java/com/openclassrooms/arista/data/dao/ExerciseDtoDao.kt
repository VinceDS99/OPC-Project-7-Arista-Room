package com.openclassrooms.arista.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.openclassrooms.arista.data.entity.ExerciseDto
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
interface ExerciseDtoDao {

    //Insère un nouvel exercice dans la base de données
    @Insert
    suspend fun insertExercise(exercise: ExerciseDto): Long


    //Récupère tous les exercices de la base de données
    //@return Un Flow qui émet une liste d'exercices
    @Query("SELECT * FROM exercise")
    fun getAllExercises(): Flow<List<ExerciseDto>>


    //Récupère tous les exercices d'un utilisateur spécifique
    @Query("SELECT * FROM exercise WHERE user_id = :userId")
    fun getExercisesByUserId(userId: Long): Flow<List<ExerciseDto>>

    //Supprime un exercice de la base de données par son identifiant
    @Query("DELETE FROM exercise WHERE id = :id")
    suspend fun deleteExerciseById(id: Long)

}