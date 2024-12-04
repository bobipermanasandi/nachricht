package id.bobipermanasandi.nachricht.ui.screen.home

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import id.bobipermanasandi.nachricht.R
import id.bobipermanasandi.nachricht.di.Injection
import id.bobipermanasandi.nachricht.model.FakeNewsDataSource
import id.bobipermanasandi.nachricht.model.News
import id.bobipermanasandi.nachricht.ui.common.UiState
import id.bobipermanasandi.nachricht.ui.components.EmptyData
import id.bobipermanasandi.nachricht.ui.components.NewsList
import id.bobipermanasandi.nachricht.ui.components.SearchInput
import id.bobipermanasandi.nachricht.ui.screen.ViewModelFactory
import id.bobipermanasandi.nachricht.ui.theme.NachrichtTheme

@Composable
fun HomeScreen(
    navigateToDetail: (Int) -> Unit,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),

) {
    val query by viewModel.query
    val message by viewModel.message
    val context = LocalContext.current

    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.searchNews(query)
            }
            is UiState.Success -> {
                HomeContent(
                    query = query,
                    onQueryChange = viewModel::searchNews,
                    newsList = uiState.data,
                    onBookmarkClicked = { id, newState ->
                        viewModel.updateNews(id, newState)
                        Toast.makeText(
                            context,
                            message,
                            Toast.LENGTH_LONG
                        ).show()
                    },
                    navigateToDetail = navigateToDetail
                )
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun HomeContent(
    query: String,
    onQueryChange: (String) -> Unit,
    newsList: List<News>,
    navigateToDetail: (Int) -> Unit,
    onBookmarkClicked: (id: Int, newState: Boolean) -> Unit,
){
    Column {
        SearchInput(
            query = query,
            onQueryChange = onQueryChange
        )
        if (newsList.isNotEmpty()) {
            NewsList(
                list = newsList,
                navigateToDetail = navigateToDetail,
                onBookmarkClicked = onBookmarkClicked
            )
        } else {
            EmptyData(
                message = stringResource(R.string.empty_data)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    NachrichtTheme{
        HomeContent(
            query = stringResource(R.string.search_hint),
            onQueryChange = {},
            newsList = FakeNewsDataSource.dummyNews,
            navigateToDetail = {},
            onBookmarkClicked = { _, _ -> }
        )

    }
}