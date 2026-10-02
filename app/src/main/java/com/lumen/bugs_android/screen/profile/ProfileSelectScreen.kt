package com.lumen.bugs_android.screen.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lumen.bugs_android.R
import com.lumen.bugs_android.model.Profile
import com.lumen.bugs_android.screen.create_profile.ZodiacImage
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileSelectScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = koinViewModel(),
) {
    val profiles by viewModel.profiles.collectAsStateWithLifecycle()
    ProfileSelectScreen(profiles, viewModel::onProfileSelected, modifier)
}

@Composable
fun ProfileSelectScreen(
    profiles: List<Profile>,
    onProfileSelected: (Profile) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = stringResource(R.string.profile_select_title),
            style = MaterialTheme.typography.headlineSmall,
        )

        if (profiles.isEmpty()) {
            Text(
                text = stringResource(R.string.profile_empty),
                style = MaterialTheme.typography.bodyLarge,
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 4.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(profiles, key = { it.id }) { profile ->
                    ProfileRow(profile = profile, onClick = { onProfileSelected(profile) })
                }
            }
        }
    }
}

@Composable
private fun ProfileRow(
    profile: Profile,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ZodiacImage(profile.zodiac, Modifier.size(48.dp))
            Spacer(Modifier.width(16.dp))
            Text(
                text = profile.name,
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}
