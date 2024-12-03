package id.bobipermanasandi.nachricht.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import id.bobipermanasandi.nachricht.R
import id.bobipermanasandi.nachricht.ui.theme.NachrichtTheme

@Composable
fun NewsItem(
    id: Int,
    title: String,
    author: String,
    urlToImage: String,
    isBookmark: Boolean,
    onBookmarkClicked: (id: Int, newState: Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .border(width = 1.5.dp, color = Color.LightGray.copy(alpha = 0.5f), shape = RoundedCornerShape(8.dp))
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(Color.Gray)
            ) {
                AsyncImage(
                    model = urlToImage,
                    contentDescription = "urlToImage",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                )
            }
            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = author,
                    overflow =TextOverflow.Ellipsis,
                    textAlign = TextAlign.Start,
                    maxLines = 2,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Light,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = title,
                    overflow =TextOverflow.Ellipsis,
                    textAlign = TextAlign.Start,
                    maxLines = 2,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W500,

                )
                Spacer(modifier = Modifier.height(4.dp))

            }
        }
        Icon(
            painter = if (isBookmark) {
                painterResource(R.drawable.ic_bookmarked_white)

            } else {
                painterResource(R.drawable.ic_bookmark_white)
            },
            contentDescription = "icon_bookmark",
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(24.dp)
                .testTag("icon_bookmark")
                .clickable { onBookmarkClicked(id, !isBookmark) }
        )
    }

}

@Preview(showBackground = true)
@Composable
fun NewsItemPreview() {
    NachrichtTheme {
        NewsItem(
            id = 0,
            title = "Women’s tennis pro Świątek suspended for banned substance",
            author = "Kyunzi Permana",
            urlToImage = "https://cdnph.upi.com/ph/st/th/3551732834721/2024/upi/7a257913607535d13fbca7ce7beb2886/v1.5/Womens-tennis-pro-witek-suspended-for-banned-substance.jpg",
            isBookmark = false,
            onBookmarkClicked = { _, _ -> }
        )
    }
}