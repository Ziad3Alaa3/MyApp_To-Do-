package data

import domain.Note
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import local.NoteEntity

@Dao
interface NoteDao {
    @Query("SELECT*FROM notes ORDER BY timestamp DESC")
    fun getNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE id =  :id ")
    suspend fun getNoteById(id: Int): NoteEntity?


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(notes: NoteEntity)

    @Delete
    suspend fun deleteNote(notes: NoteEntity)

}