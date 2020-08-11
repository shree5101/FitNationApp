package com.example.android.fitnationapp.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.room.Room
import com.example.android.fitnationapp.db.RunningDatabase
import com.example.android.fitnationapp.other.Constants.KEY_FIRST_TIME_TOGGLE
import com.example.android.fitnationapp.other.Constants.KEY_NAME
import com.example.android.fitnationapp.other.Constants.KEY_WEIGHT
import com.example.android.fitnationapp.other.Constants.RUNNING_DATABASE_NAME
import com.example.android.fitnationapp.other.Constants.SHARED_PREFERENCES_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

//Module annotation is used to tell Dagger that this is a module.
//InstallIn annotation is used tell dagger to create and delete the dependencies
// when the application is started and killed.
//Like applicationComponent, many other types is available as in
//fragmentComponent, serviceComponent and activityComponent.
@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    //This function is used to provide the database for a class which uses this database.
    //For the context, just mentioning the context is not enough as dagger won't
    //know what context to look for. That's why we annotate it with @ApplicationContext
    //Provides annotation is used ti tell dagger that this function provides something.
    //Singleton annotations is used to tell dagger that throughout the app,
    //we need only one instance of the database. If we don't use it then we'll have a new
    //instance of database every time we call this function.
    @Singleton
    @Provides
    fun provideRunningDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        RunningDatabase::class.java,
        RUNNING_DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideRunDao(db: RunningDatabase) = db.getRunDao()

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext app: Context): SharedPreferences =
        app.getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideName(sharedPref: SharedPreferences) = sharedPref.getString(KEY_NAME, "") ?: ""

    @Singleton
    @Provides
    fun provideWeight(sharedPref: SharedPreferences) = sharedPref.getInt(KEY_WEIGHT, 80)

    @Singleton
    @Provides
    fun provideFirstTimeToggle(sharedPref: SharedPreferences) =
        sharedPref.getBoolean(KEY_FIRST_TIME_TOGGLE, true)
}