package com.example.mathtraining.database

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface UserDao {
    @Query("SELECT * FROM user_profile")
    fun getInfoUser(): Flow<User>

    @Query("SELECT isActiveNotification FROM user_profile")
    fun getEnableNotification(): LiveData<Boolean>

    @Query("SELECT id FROM user_profile")
    fun getId(): LiveData<Int>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)

    @Query("UPDATE user_profile SET name=:name WHERE id=:id")
    suspend fun updateName(name: String, id: Int)

    @Query("UPDATE user_profile SET name=:surname WHERE id=:id")
    suspend fun updateSurname(surname: String, id: Int)

    @Query("UPDATE user_profile SET isActiveNotification=:enable WHERE id=:id")
    suspend fun updateIsActiveNotification(enable: Boolean, id: Int)

}