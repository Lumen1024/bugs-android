package com.lumen.bugs_android

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.lumen.bugs_android.register.RegisterScreenRoot

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
        Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            RegisterScreenRoot(modifier.padding(innerPadding))
        }
    }
}