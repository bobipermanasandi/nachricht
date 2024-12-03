package id.bobipermanasandi.nachricht.model

data class News(
    val id: Int,
    val title: String,
    val author: String,
    val source: String,
    val publishedAt: String,
    val url: String,
    val urlToImage: String,
    val description: String,
    var isBookmark: Boolean = false
)
