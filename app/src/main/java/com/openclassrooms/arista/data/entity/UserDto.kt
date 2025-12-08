package com.openclassrooms.arista.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entité Room représentant un utilisateur dans la base de données.
 *
 * Cette classe DTO (Data Transfer Object) est la représentation persistante
 * d'un utilisateur dans la couche de données. Elle est mappée à la table "user" en SQLite.
 *
 * @property id Identifiant unique de l'utilisateur (clé primaire)
 * @property name Nom complet de l'utilisateur
 * @property email Adresse email (utilisée pour l'authentification)
 * @property password Mot de passe (devrait être hashé en production)
 */

@Entity(tableName = "user")
data class UserDto(

    //Clé primaire de la table user.
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0,

    //Nom complet de l'utilisateur.
    @ColumnInfo(name = "name")
    var name: String,

    //Adresse email de l'utilisateur.
    @ColumnInfo(name = "email")
    var email: String,

    //Mot de passe de l'utilisateur.
    @ColumnInfo(name = "password")
    var password: String

)