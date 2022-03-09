package com.samr.data.di

import androidx.room.Room
import com.samr.data.BuildConfig
import com.samr.data.local.Database
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            Database::class.java,
            BuildConfig.DB_NAME
        ).allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
}
