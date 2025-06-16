package data


import Note
import local.NoteEntity


fun NoteEntity.toNote(): NoteEntity{

    return NoteEntity(id = id , title = title , timestamp = timestamp)
}
fun Note.toEntity(): NoteEntity{

    return NoteEntity(id = id , title = title , timestamp = timestamp)
}