package com.example.hiki.domain.repository

import com.example.hiki.domain.model.dao.UserDao
import com.example.hiki.domain.model.entity.User

class UserRepository (private val userDao: UserDao){
    suspend fun insert(user: User) {
        userDao.insert(user)
    }

    suspend fun getUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email)
    }
}