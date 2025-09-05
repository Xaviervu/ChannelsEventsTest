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

    private val eventMutableChannel = Channel<Int>()
    val eventChannel = eventMutableChannel.receiveAsFlow()

    var progressbarShown = false
    private var navigationJob: Job? = null
    private var delay = 0

    fun buttonPressed(){
        navigationJob = viewModelScope.launch {
            delay = 3
            eventMutableChannel.send(delay)
            while(delay > 0){
                delay(1000)
                delay --
                eventMutableChannel.send(delay)
            }
        }
    }
    fun cancelNavigation(){
        navigationJob?.cancel()
        delay = 0
    }
}