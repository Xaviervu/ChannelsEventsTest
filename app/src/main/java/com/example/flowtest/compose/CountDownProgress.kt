package com.example.flowtest.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class CountDownProgress {
    @Composable
    fun CountDownProgressComposable(modifier: Modifier = Modifier,dataFlow: Flow<Int>) {
        val timer = dataFlow.collectAsStateWithLifecycle(0)
        CircularProgressIndicator(
            color = MaterialTheme.colors.surface,
            modifier = modifier
        )
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
        ) {
            Text(
                "${timer.value}",
                color = MaterialTheme.colors.onPrimary,
                fontSize = 20.sp
            )
        }

    }

    @Preview()
    @Composable
    fun CountDownProgressComposablePreview() {
        CountDownProgressComposable(
            Modifier
                .height(45.dp)
                .height(45.dp),
            dataFlow = flowOf(5)
        )
    }
}