package com.lumen.bugs_android.screen.records

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.lumen.bugs_android.model.GameRecord
import com.lumen.bugs_android.util.formatDateLocal
import org.koin.androidx.compose.koinViewModel

@Composable
fun RecordsScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: RecordsViewModel = koinViewModel(),
) {
    val records by viewModel.records.collectAsStateWithLifecycle()
    RecordsScreen(records, modifier)
}

@Composable
fun RecordsScreen(
    records: List<GameRecord>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = stringResource(R.string.records_title),
            style = MaterialTheme.typography.headlineSmall,
        )

        if (records.isEmpty()) {
            Text(
                text = stringResource(R.string.records_empty),
                style = MaterialTheme.typography.bodyLarge,
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentPadding = PaddingValues(vertical = 4.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(records, key = { it.id }) { record ->
                    RecordRow(record)
                }
            }
        }
    }
}

@Composable
private fun RecordRow(
    record: GameRecord,
    modifier: Modifier = Modifier,
) {
    Card(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = record.name,
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = stringResource(
                        R.string.records_subtitle,
                        stringResource(record.difficulty.labelRes),
                        formatDateLocal(record.finishedAt),
                    ),
                    style = MaterialTheme.typography.bodySmall,
                )
            }
            Text(
                text = record.score.toString(),
                style = MaterialTheme.typography.titleLarge,
            )
        }
    }
}
