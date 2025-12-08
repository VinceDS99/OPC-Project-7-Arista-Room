package com.openclassrooms.arista.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Entité Room représentant un cycle de sommeil dans la base de données.
 *
 * Cette classe DTO (Data Transfer Object) modélise les données de sommeil
 * persistées dans la table "sleep" de la base SQLite.
 *
 * @property id Identifiant unique du cycle de sommeil
 * @property userId Identifiant de l'utilisateur concerné
 * @property startTime Timestamp du début du sommeil (en millisecondes)
 * @property duration Durée totale du sommeil en minutes
 * @property quality Note de qualité du sommeil (échelle subjective)
 */

@Entity(
    tableName = "sleep",

    //Clé étrangère : chaque cycle de sommeil est lié à un utilisateur.
    foreignKeys = [
        ForeignKey(
            entity = UserDto::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],

    indices = [Index(value = ["user_id"])]
)

data class SleepDto(

    //Clé primaire auto-générée
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0,

    @ColumnInfo(name = "user_id")
    var userId: Long,

    //Début du sommeil en millisecondes
    @ColumnInfo(name = "start_time")
    var startTime: Long,

    //Durée du sommeil en minutes.
    @ColumnInfo(name = "duration")
    var duration: Int,

    //Note de qualité du sommeil.
    @ColumnInfo(name = "quality")
    var quality: Int

)