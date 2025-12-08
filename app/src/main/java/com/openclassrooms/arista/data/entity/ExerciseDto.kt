package com.openclassrooms.arista.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Index

/**
 * Entité Room représentant un exercice physique dans la base de données.
 *
 * Cette classe DTO (Data Transfer Object) est la représentation persistante
 * d'un exercice. Elle est mappée à la table "exercise" dans SQLite.
 *
 * @property id Identifiant unique de l'exercice (auto-généré par Room)
 * @property userId Identifiant de l'utilisateur qui a effectué l'exercice
 * @property startTime Timestamp Unix (en millisecondes) du début de l'exercice
 * @property duration Durée de l'exercice en minutes
 * @property category Type d'exercice (Running, Swimming, etc.) stocké comme String
 * @property intensity Intensité de l'exercice sur une échelle de 1 à 10
 */

@Entity(
    tableName = "exercise",

    // Définition d'une clé étrangère vers la table "user"
    foreignKeys = [
        ForeignKey(
            entity = UserDto::class,            // Table parente
            parentColumns = ["id"],             // Colonne référencée dans la table parente
            childColumns = ["user_id"],         // Colonne de cette table qui fait référence
            onDelete = ForeignKey.CASCADE       // Si un user est supprimé, ses exercices aussi
        )
    ],

    indices = [Index(value = ["user_id"])]
)


data class ExerciseDto(

    //Clé primaire auto-générée
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0,

    //Identifiant de l'utilisateur propriétaire de cet exercice.
    @ColumnInfo(name = "user_id")
    var userId: Long,

    //Date et heure de début de l'exercice, stockée en millisecondes depuis l'epoch Unix
    @ColumnInfo(name = "start_time")
    var startTime: Long,

    //Durée de l'exercice en minutes.
    @ColumnInfo(name = "duration")
    var duration: Int,

    //Catégorie de l'exercice (Running, Swimming, Football, Walking, Riding).
    @ColumnInfo(name = "category")
    var category: String,

    //Intensité de l'exercice sur une échelle de 1 (très léger) à 10 (très intense).
    @ColumnInfo(name = "intensity")
    var intensity: Int

)