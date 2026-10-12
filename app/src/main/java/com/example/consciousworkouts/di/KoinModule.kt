package com.example.consciousworkouts.di

import androidx.room3.Room
import com.example.consciousworkouts.data.AppRepositoryImpl
import com.example.consciousworkouts.data.MIGRATION_10_11
import com.example.consciousworkouts.data.MIGRATION_11_12
import com.example.consciousworkouts.data.MIGRATION_12_13
import com.example.consciousworkouts.data.MIGRATION_13_14
import com.example.consciousworkouts.data.MIGRATION_14_15
import com.example.consciousworkouts.data.MIGRATION_15_16
import com.example.consciousworkouts.data.MIGRATION_16_17
import com.example.consciousworkouts.data.MIGRATION_17_18
import com.example.consciousworkouts.data.MIGRATION_18_19
import com.example.consciousworkouts.data.MIGRATION_8_9
import com.example.consciousworkouts.data.MIGRATION_9_10
import com.example.consciousworkouts.data.WorkoutDatabase
import com.example.consciousworkouts.domain.AppRepository
import com.example.consciousworkouts.presentation.create.CreateViewModel
import com.example.consciousworkouts.presentation.past.PastViewModel
import com.example.consciousworkouts.presentation.today.TodayViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


val appModule = module {


    viewModel { TodayViewModel(get()) }

    viewModel { CreateViewModel(get()) }

    viewModel { PastViewModel(get()) }

    single {
        Room.databaseBuilder(
            context = androidContext(),
            klass = WorkoutDatabase::class.java,
            name = "workout_db"
        ).addMigrations(MIGRATION_8_9, MIGRATION_9_10, MIGRATION_10_11, MIGRATION_11_12,
            MIGRATION_12_13, MIGRATION_13_14, MIGRATION_14_15, MIGRATION_15_16, MIGRATION_16_17,
            MIGRATION_17_18, MIGRATION_18_19
        ).build()
    }

    single <AppRepository> { AppRepositoryImpl(get()) } // This is the repository


    single {get<WorkoutDatabase>().getDao()} // This is the Dao















}