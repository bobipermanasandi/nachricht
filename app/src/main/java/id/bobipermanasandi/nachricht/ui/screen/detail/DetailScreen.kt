package id.bobipermanasandi.nachricht.ui.screen.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import id.bobipermanasandi.nachricht.R
import id.bobipermanasandi.nachricht.di.Injection
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
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(stringResource(R.string.detail))
    }
}


@Preview(showBackground = true)
@Composable
fun DetailContentPreview() {
    NachrichtTheme {
        DetailScreen(
            newsId = 0,
            navigateBack = {},
        )
    }
}