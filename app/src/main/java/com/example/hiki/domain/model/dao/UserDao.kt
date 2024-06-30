package com.example.hiki.domain.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.hiki.domain.model.entity.User

@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: User)

    @Query("SELECT * FROM user_table WHERE email = :email")
    suspend fun getUserByEmail(email: String): User?

}