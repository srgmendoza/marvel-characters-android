package com.samr.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.samr.data.local.dao.CharactersDao
import com.samr.data.local.converters.CharacterConverter
import com.samr.domain.models.Character

@Database(
    entities = [Character::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(CharacterConverter::class)
abstract class Database : RoomDatabase() {
    abstract fun getCharactersDao(): CharactersDao
}