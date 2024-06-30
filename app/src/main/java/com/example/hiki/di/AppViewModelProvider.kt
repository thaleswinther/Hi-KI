package com.example.hiki.di

import android.content.Context
import com.example.hiki.domain.database.HikiDataBase
import com.example.hiki.domain.model.dao.UserDao
import com.example.hiki.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppViewModelProvider {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): HikiDataBase {
        return HikiDataBase.getDatabase(context)
    }

    @Provides
    fun provideUserDao(database: HikiDataBase): UserDao {
        return database.userDao()
    }

    @Provides
    fun provideUserRepository(userDao: UserDao): UserRepository {
        return UserRepository(userDao)
    }
}
