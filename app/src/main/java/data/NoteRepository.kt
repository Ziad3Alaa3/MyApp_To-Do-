package data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import local.NoteEntity
import domain.Note  // ← هنا تأكد إنك عامل import للكلاس Note الصحيح من package domain

class NoteRepository(private val dao: NoteDao) {

    fun getNotes(): Flow<List<Note>> {
        return dao.getNotes().map { list ->
            list.map { entity ->
                Note(
                    id = entity.id,
                    title = entity.title,
                    timestamp = entity.timestamp
                )
            }
        }
    }

    suspend fun getNoteById(id: Int): Note? {
        val entity = dao.getNoteById(id)
        return entity?.let {
            Note(
                id = it.id,
                title = it.title,
                timestamp = it.timestamp
            )
        }
    }

    suspend fun insertNote(note: Note) {
        val entity = NoteEntity(
            id = note.id,
            title = note.title,
            timestamp = note.timestamp
        )
        dao.insertNote(entity)
    }

    suspend fun deleteNote(note: Note) {
        val entity = NoteEntity(
            id = note.id,
            title = note.title,
            timestamp = note.timestamp
        )
        dao.deleteNote(entity)
    }
}
