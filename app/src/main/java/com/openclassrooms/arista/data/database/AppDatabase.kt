package com.openclassrooms.arista.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.openclassrooms.arista.data.dao.ExerciseDtoDao
import com.openclassrooms.arista.data.dao.SleepDtoDao
import com.openclassrooms.arista.data.dao.UserDtoDao
import com.openclassrooms.arista.data.entity.ExerciseDto
import com.openclassrooms.arista.data.entity.SleepDto
import com.openclassrooms.arista.data.entity.UserDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneOffset

/**
 * Classe principale de la base de données Room pour l'application Arista.
 *
 * Cette classe abstraite définit la configuration de la base de données SQLite,
 * incluant toutes les entités (tables) et les DAOs (interfaces d'accès aux données).
 *
 * Pattern Singleton : Une seule instance de la base de données existe pour toute
 * l'application, évitant les problèmes de synchronisation et de performance.
 *
 */

@Database(
    //Liste des classes DTO qui seront transformées en tables SQLite
    entities = [UserDto::class, SleepDto::class, ExerciseDto::class],

    //Numéro de version de la base incrémenté à chaque modification de schéma
    version = 2,  // Version 2 : mise à jour après modifications du schéma

    //False pour ne pas exporter le schéma JSON
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {

    //Fournit l'accès au DAO des utilisateurs
    abstract fun userDtoDao(): UserDtoDao

    //Fournit l'accès au DAO des cycles de sommeil
    abstract fun sleepDtoDao(): SleepDtoDao

    //Fournit l'accès au DAO des exercices physiques
    abstract fun exerciseDtoDao(): ExerciseDtoDao



    //Callback personnalisé appelé UNIQUEMENT lors de la première création de la base
    //Il permet d'initialiser la base avec des données de démonstration ou des données par défaut nécessaires
    private class AppDatabaseCallback(
        private val scope: CoroutineScope
    ) : Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            // Vérifie que l'instance singleton existe avant de peupler
            INSTANCE?.let { database ->
                // Lance une coroutine pour insérer les données initiales
                scope.launch {
                    populateDatabase(database.sleepDtoDao(), database.userDtoDao())
                }
            }
        }
    }


    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, coroutineScope: CoroutineScope): AppDatabase {

            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "AristaDB"
                )
                    .addCallback(AppDatabaseCallback(coroutineScope))
                    .build()

                INSTANCE = instance
                instance
            }
        }

        /**
         * Peuple la base de données avec des données initiales de démonstration
         *
         * Cette fonction suspend est appelée lors de la première création de la base
         * Elle insère un utilisateur par défaut et quelques cycles de sommeil associés
         * pour permettre de tester l'application
         *
         */
        suspend fun populateDatabase(sleepDao: SleepDtoDao, userDtoDao: UserDtoDao) {
            //Création d'un utilisateur par défaut avec un ID auto-généré
            val userId = userDtoDao.insertUser(
                UserDto(
                    name = "John Doe",
                    email = "john.doe@example.com",
                    password = "password123"  //Mot de passe en clair à hasher
                )
            )

            //Création de deux cycles de sommeil
            sleepDao.insertSleep(
                SleepDto(
                    userId = userId,  // Lien avec l'utilisateur créé
                    startTime = LocalDateTime.now()
                        .minusDays(1)                     // Il y a 1 jour
                        .atZone(ZoneOffset.UTC)                // Définit le fuseau horaire UTC
                        .toInstant()                           // Convertit en Instant
                        .toEpochMilli(),                       // Convertit en millisecondes
                    duration = 500,  // Durée de sommeil
                    quality = 4      // Qualité du sommeil
                )
            )


            sleepDao.insertSleep(
                SleepDto(
                    userId = userId,
                    startTime = LocalDateTime.now()
                        .minusDays(2)                          // Il y a 2 jours
                        .atZone(ZoneOffset.UTC)
                        .toInstant()
                        .toEpochMilli(),
                    duration = 450,  // Durée de sommeil
                    quality = 3      // Qualité du sommeil
                )
            )

            //Pas d'exercices insérés ici, ils seront ajoutés par l'utilisateur
        }
    }
}