package id.bobipermanasandi.nachricht.ui.screen.detail

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import id.bobipermanasandi.nachricht.R
import id.bobipermanasandi.nachricht.di.Injection
import id.bobipermanasandi.nachricht.ui.common.UiState
import id.bobipermanasandi.nachricht.ui.screen.ViewModelFactory
import id.bobipermanasandi.nachricht.ui.theme.NachrichtTheme

@Composable
fun DetailScreen(
    newsId: Int,
    navigateBack: () -> Unit,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    )
) {

    val message by viewModel.message
    val context = LocalContext.current

    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let {uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getNewsById(newsId)
            }
            is UiState.Success -> {
                val news = uiState.data
                DetailContent(
                    id = news.id,
                    title = news.title,
                    author = news.author,
                    source = news.source,
                    publishedAt = news.publishedAt,
                    url = news.url,
                    urlToImage = news.urlToImage,
                    description = news.description,
                    isBookmark = news.isBookmark,
                    navigateBack = navigateBack,
                    onBookmarkClicked = { id, newState ->
                        viewModel.updateNews(id, newState)
                        Toast.makeText(
                            context,
                            message,
                            Toast.LENGTH_LONG
                        ).show()
                    },
                )
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun DetailContent(
    id: Int,
    title: String,
    author: String,
    source: String,
    publishedAt: String,
    url: String,
    urlToImage: String,
    description: String,
    isBookmark: Boolean,
    navigateBack: () -> Unit,
    onBookmarkClicked: (id: Int, state: Boolean) -> Unit
){
    val uriHandler = LocalUriHandler.current
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(bottom = 16.dp)
        ) {
            AsyncImage(
                model = urlToImage,
                contentDescription = "urlToImage",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "By : $author ($source)",
                fontWeight = FontWeight.W400,
                fontSize = 12.sp,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = publishedAt,
                fontWeight = FontWeight.W200,
                fontSize = 12.sp,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            )
            HorizontalDivider(
                color = Color.LightGray.copy(alpha = 0.5f),
                thickness = 0.8.dp,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            )
            Text(
                text = description,
                fontSize = 14.sp,
                lineHeight = 28.sp,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(50.dp))
            Button(
                onClick = {
                    uriHandler.openUri(url)
                },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                enabled = true,
                contentPadding = PaddingValues(12.dp)
            ) {
                Text(text = "Go To Article")
            }
        }
        IconButton(
            onClick = navigateBack,
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp)
                .align(Alignment.TopStart)
                .clip(CircleShape)
                .size(40.dp)
                .background(Color.White)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(R.string.back),
            )
        }
        IconButton(
            onClick = {
                onBookmarkClicked(id, isBookmark)
            },
            modifier = Modifier
                .padding(end = 16.dp, top = 16.dp)
                .align(Alignment.TopEnd)
                .clip(CircleShape)
                .size(40.dp)
                .background(Color.White)
        ) {
            Icon(
                painter = if (isBookmark) {
                    painterResource(R.drawable.ic_bookmarked_white)

                } else {
                    painterResource(R.drawable.ic_bookmark_white)
                },
                contentDescription = if (!isBookmark) stringResource(R.string.add_bookmark) else stringResource(
                    R.string.delete_bookmark),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailContentPreview() {
    NachrichtTheme {
        DetailContent(
            id = 5,
            title = "Australian Open Fast Facts",
            author = "Marc Little",
            source = "CNN Asia",
            publishedAt = "2024-11-28T23:15:28+00:00",
            url = "https://www.cnn.com/2013/10/30/world/australian-open-fast-facts/index.html",
            urlToImage = "https://cdn.cnn.com/cnnnext/dam/assets/220105050815-02-aus-open-prep-file-10272021-super-169.jpg",
            description = "Read CNN's Fast Facts about the Australian Open, one of four competitions that make up the \"Grand Slam\" in professional tennis.",
            isBookmark = true,
            navigateBack = {},
            onBookmarkClicked = { _, _ -> }
        )
    }
}