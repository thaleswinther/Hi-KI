package com.example.hiki.domain.repository

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.example.hiki.domain.database.HikiDataBase
import com.example.hiki.domain.model.dao.UserDao
import com.example.hiki.domain.model.entity.User
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [29], manifest=Config.NONE)
class UserRepositoryInsertTest {

    private lateinit var database: HikiDataBase
    private lateinit var userDao: UserDao
    private lateinit var userRepository: UserRepository

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(
            context,
            HikiDataBase::class.java
        ).allowMainThreadQueries().build()

        userDao = database.userDao()
        userRepository = UserRepository(userDao)
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun `test insert user`() = runBlocking {
        val user = User(1, "test@gmail.com", "user_test", "password_test")
        userRepository.insert(user)

        val retrievedUser = userRepository.getUserByEmail("test@gmail.com")

        assertEquals(user, retrievedUser)
    }
}
