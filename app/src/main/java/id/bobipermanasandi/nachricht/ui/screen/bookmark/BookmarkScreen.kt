package id.bobipermanasandi.nachricht.ui.screen.bookmark

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import id.bobipermanasandi.nachricht.R
import id.bobipermanasandi.nachricht.di.Injection
import id.bobipermanasandi.nachricht.model.News
import id.bobipermanasandi.nachricht.ui.common.UiState
import id.bobipermanasandi.nachricht.ui.components.EmptyData
import id.bobipermanasandi.nachricht.ui.components.NewsList
import id.bobipermanasandi.nachricht.ui.screen.ViewModelFactory
import id.bobipermanasandi.nachricht.ui.theme.NachrichtTheme

@Composable
fun BookmarkScreen(
    navigateToDetail: (Int) -> Unit,
    viewModel: BookmarkViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    )
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getBookmarkNews()
            }
            is UiState.Success -> {
                BookmarkContent(
                    newsList = uiState.data,
                    navigateToDetail = navigateToDetail,
                    onBookmarkClicked = {id, newState ->
                        viewModel.updateNews(id, newState)
                    }
                )
            }
            is UiState.Error -> {}
        }
    }
}


@Composable
fun BookmarkContent(
    newsList: List<News>,
    navigateToDetail: (Int) -> Unit,
    onBookmarkClicked: (id: Int, newState: Boolean) -> Unit,
    modifier: Modifier = Modifier,
){
    Column(
        modifier = modifier
    ) {
        if (newsList.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            NewsList(
                list = newsList,
                navigateToDetail = navigateToDetail,
                onBookmarkClicked = onBookmarkClicked
            )
        }else {
            EmptyData(
                message = stringResource(R.string.empty_data)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BookmarkScreenPreview() {
    NachrichtTheme {
        BookmarkScreen(
            navigateToDetail = {},
        )
    }
}