package com.lumen.bugs_android.register

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import coil3.compose.SubcomposeAsyncImage
import com.lumen.bugs_android.model.Zodiac

@Composable
fun ZodiacImage(zodiac: Zodiac?, modifier: Modifier = Modifier) {
    if (zodiac != null)
        SubcomposeAsyncImage(
            model = zodiac.link,
            contentDescription = stringResource(zodiac.titleRes),
            modifier = modifier.clip(CircleShape),
            loading = { CircularProgressIndicator() },
        )
    else
        Icon(
            Icons.Default.AutoAwesome,
            null, modifier,
            tint = MaterialTheme.colorScheme.primary
        )
}