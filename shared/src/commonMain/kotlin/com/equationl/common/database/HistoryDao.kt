package com.equationl.common.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.equationl.common.dataModel.HistoryData

@Dao
interface HistoryDao {
    @Query("select * from history order by id DESC")
    suspend fun getAll(): List<HistoryData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: HistoryData)

    @Update
    suspend fun update(item: HistoryData)

    @Delete
    suspend fun delete(item: HistoryData)

    @Query("DELETE FROM history")
    suspend fun deleteAll()
}