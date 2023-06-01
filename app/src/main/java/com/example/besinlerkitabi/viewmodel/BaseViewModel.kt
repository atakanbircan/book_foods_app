package com.example.besinlerkitabi.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

open class BaseViewModel(application: Application) : AndroidViewModel(application),CoroutineScope {

    private val job = Job() // arkaplandaki işleri temsil eder.
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onCleared() {
        super.onCleared()
        job.cancel()  // işlemler bitince thread da arkada yaptığı işi biritmesi için..
    }
}


