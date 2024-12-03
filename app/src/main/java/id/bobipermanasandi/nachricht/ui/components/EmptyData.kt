package id.bobipermanasandi.nachricht.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import id.bobipermanasandi.nachricht.R
import id.bobipermanasandi.nachricht.ui.theme.NachrichtTheme

@Composable
fun EmptyData(
    message: String,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = message,
            fontSize = 20.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EmptyDataPreview() {
    NachrichtTheme{
        EmptyData(stringResource(R.string.empty_data))
    }
}