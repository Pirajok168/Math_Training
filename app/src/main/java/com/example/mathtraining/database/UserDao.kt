package com.example.mathtraining.database

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Qualifier


@Dao
interface UserDao {
    @Query("SELECT * FROM user_profile")
    fun getInfoUser(): Flow<User?>

    @Query("SELECT isActiveNotification FROM user_profile")
    fun getEnableNotification(): LiveData<Boolean>

    @Query("SELECT health FROM user_profile")
    fun getHealthUser(): LiveData<Int>

    @Query("SELECT health FROM user_profile")
    fun getHealthUser2(): Int

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


    @Query("SELECT EXISTS(SELECT * FROM user_profile)")
    suspend fun isExists(): Boolean


    @Transaction
    @Query("SELECT * FROM user_profile")
    fun getActiveUser(): LiveData<ActiveUser>

    @Insert
    suspend fun insert(statistic: Statistic)


    @Update
    suspend fun updateStatistic(statistic: Statistic)

    @Transaction
    suspend fun testInsert(userActiveUser: ActiveUser){
        insert(userActiveUser.user)

        userActiveUser.listStatistic.forEach {
           insert(it)
        }
    }


}