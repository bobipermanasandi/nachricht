package id.bobipermanasandi.nachricht.ui.components

import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import id.bobipermanasandi.nachricht.model.News

@Composable
fun NewsList(
    list: List<News>,
    navigateToDetail: (Int) -> Unit,
    onBookmarkClicked: (id: Int, newState: Boolean) -> Unit,
    modifier: Modifier = Modifier
){
    LazyColumn(
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        items(list, key = {it.id}) { item ->
            NewsItem(
                id = item.id,
                title = item.title,
                author = item.author,
                publishedAt = item.publishedAt,
                urlToImage = item.urlToImage,
                isBookmark = item.isBookmark,
                onBookmarkClicked = onBookmarkClicked,
                modifier = Modifier
                    .animateItem(tween(durationMillis = 200))
                    .clickable { navigateToDetail(item.id) }

            )

        }
    }
}