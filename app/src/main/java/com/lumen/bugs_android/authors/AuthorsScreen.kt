package com.lumen.bugs_android.authors

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.lumen.bugs_android.model.Author

private val authors = listOf(
    Author("Прозоренко Константин", "https://i.pinimg.com/736x/2f/39/0a/2f390ad6e0778fa6137bd0185a2a0b1d.jpg"),
    Author("Бекбауов Михаил", "https://i.pinimg.com/736x/7f/39/73/7f3973acdc81b35bb5fe5d16b078447a.jpg"),
)

@Composable
fun AuthorsScreenRoot(modifier: Modifier = Modifier) {
    AuthorsScreen(authors, modifier)
}

@Composable
fun AuthorsScreen(
    authors: List<Author>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(authors, key = { it.name }) { author ->
            AuthorItem(
                author = author,
                modifier = Modifier
                    .fillParentMaxHeight(0.5f)
                    .padding(8.dp),
            )
        }
    }
}

@Composable
private fun AuthorItem(author: Author, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            AsyncImage(
                model = author.photoUrl,
                contentDescription = author.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth(0.55f)
                    .aspectRatio(1f)
                    .clip(CircleShape),
            )
            Spacer(Modifier.height(16.dp))
            Text(
                text = author.name,
                style = MaterialTheme.typography.headlineSmall,
            )
        }
    }
}
