package id.bobipermanasandi.nachricht.data

import id.bobipermanasandi.nachricht.model.FakeNewsDataSource
import id.bobipermanasandi.nachricht.model.News
import kotlinx.coroutines.flow.*

class NewsRepository {
    private val dummyNews = mutableListOf<News>()

    init {
        if(dummyNews.isEmpty()) {
            FakeNewsDataSource.dummyNews.forEach{
                dummyNews.add(it)
            }
        }
    }

    fun getNewsById(newsId: Int) : News {
        return dummyNews.first {
            it.id == newsId
        }
    }

    fun getBookmarkNews(): Flow<List<News>> {
        return flowOf(dummyNews.filter { it.isBookmark })
    }

    fun searchNews(query: String) = flow {
        val data = dummyNews.filter {
            it.title.contains(query, ignoreCase = true)
        }
        emit(data)
    }

    fun updateNews(newsId: Int, newState: Boolean): Flow<Boolean> {
        val index = dummyNews.indexOfFirst { it.id == newsId }
        val result = if (index >= 0) {
            val news = dummyNews[index]
            dummyNews[index] = news.copy(isBookmark = newState)
            true
        } else {
            false
        }

        return flowOf(result)
    }

    companion object {
        @Volatile
        private var instance: NewsRepository? = null

        fun getInstance(): NewsRepository =
            instance ?: synchronized(this) {
                NewsRepository().apply {
                    instance = this
                }
            }
    }
}