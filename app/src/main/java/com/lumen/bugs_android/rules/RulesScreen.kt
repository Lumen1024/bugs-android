package com.lumen.bugs_android.rules

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.unit.dp
import com.lumen.bugs_android.R

@Composable
fun RulesScreenRoot(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val html = remember(context) {
        context.resources.openRawResource(R.raw.rules).bufferedReader().use { it.readText() }
    }
    RulesScreen(html, modifier)
}

@Composable
fun RulesScreen(
    html: String,
    modifier: Modifier = Modifier,
) {
    val text = remember(html) { AnnotatedString.fromHtml(html) }
    Text(
        text = text,
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
    )
}
