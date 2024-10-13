package com.example.flowtest

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MainViewModel(app: Application):AndroidViewModel(app) {

    private val eventMutableChannel = Channel<String>()
    val eventChannel = eventMutableChannel.receiveAsFlow()

    var progressbarShown = false
    fun buttonPressed(){
        viewModelScope.launch {
            delay(5000)
            eventMutableChannel.send("2!")
        }
    }
}