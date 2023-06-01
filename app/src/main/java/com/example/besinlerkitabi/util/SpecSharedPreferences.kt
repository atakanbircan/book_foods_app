package com.example.besinlerkitabi.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.core.content.edit

class SpecSharedPreferences {

    companion object {
        private val TIME="time"
        private var sharedPreferences: SharedPreferences? = null


        @Volatile
        private var instance: SpecSharedPreferences? = null

        private val lock = Any()
        operator fun invoke(context: Context): SpecSharedPreferences = instance ?: synchronized(lock){
            instance?: dospecSharedPreferences(context).also {
                instance = it
            }
        }

        private fun dospecSharedPreferences(context: Context):SpecSharedPreferences{
            sharedPreferences =androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
            PreferenceManager.getDefaultSharedPreferences(context)
            return SpecSharedPreferences()

        }
            }

    fun timerecord(time:Long){
        sharedPreferences?.edit(commit = true){
            putLong(TIME,time)
        }
    }
    fun getTime()= sharedPreferences?.getLong(TIME,0)
}