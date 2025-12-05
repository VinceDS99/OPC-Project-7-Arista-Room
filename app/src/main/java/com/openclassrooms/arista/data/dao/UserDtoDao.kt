package com.openclassrooms.arista.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.openclassrooms.arista.data.entity.UserDto
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDtoDao {
    @Insert
    suspend fun insertUser(user: UserDto): Long

    @Query("SELECT * FROM user")
    fun getAllUsers(): Flow<List<UserDto>>

    @Query("SELECT * FROM user WHERE id = :id")
    fun getUserById(id: Long): Flow<UserDto?>

    @Query("SELECT * FROM user WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): UserDto?

    @Update
    suspend fun updateUser(user: UserDto)

    @Query("DELETE FROM user WHERE id = :id")
    suspend fun deleteUserById(id: Long)
}
