package com.jgarnier.menuapplication.di

import android.app.Application
import androidx.room.Room
import com.jgarnier.menuapplication.data.database.DailyMenuDatabase
import com.jgarnier.menuapplication.data.repository.DailyMealRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideDailyMenuDatabase(application: Application): DailyMenuDatabase {
        return Room
            .databaseBuilder(application, DailyMenuDatabase::class.java, "menu_database.db")
            .build()
    }

    @Singleton
    @Provides
    fun provideDailyMealRepository(dailyMenuDatabase: DailyMenuDatabase): DailyMealRepository {
        return DailyMealRepository(dailyMenuDatabase.dailyMealsDao())
    }

}