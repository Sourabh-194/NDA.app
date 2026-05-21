package com.example.data

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "study_logs")
data class StudyLog(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val topic: String,
    val durationMinutes: Int,
    val score: Int? = null,
    val timestamp: Long = System.currentTimeMillis()
)

@Dao
interface StudyLogDao {
    @Query("SELECT * FROM study_logs ORDER BY timestamp DESC")
    fun getAllLogs(): Flow<List<StudyLog>>

    @Insert
    suspend fun insertLog(log: StudyLog)

    @Query("SELECT * FROM study_logs ORDER BY timestamp DESC LIMIT 10")
    suspend fun getRecentLogsSync(): List<StudyLog>
}

@Database(entities = [StudyLog::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun studyLogDao(): StudyLogDao
}
