package com.example.flowtest

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MainViewModel(app: Application):AndroidViewModel(app) {

    private val progressMutableChannel = Channel<Int>()
    val progressChannel = progressMutableChannel.receiveAsFlow()

    private val navigateMutableChannel = Channel<Unit>()
    val navigateChannel = navigateMutableChannel.receiveAsFlow()

    var progressbarShown = false
    private var navigationJob: Job? = null
    private var delay = 0

    fun buttonPressed(){
        progressbarShown = true
        navigationJob = viewModelScope.launch {
            delay = 5
            progressMutableChannel.send(delay)
            while(delay > 0){
                delay(1000)
                delay --
                progressMutableChannel.send(delay)
            }
            navigateMutableChannel.send(Unit)
        }
    }
    fun cancelNavigation(){
        navigationJob?.cancel()
        delay = 0
    }
}