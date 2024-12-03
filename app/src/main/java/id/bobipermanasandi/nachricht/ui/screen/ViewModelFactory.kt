package id.bobipermanasandi.nachricht.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.bobipermanasandi.nachricht.data.NewsRepository
import id.bobipermanasandi.nachricht.ui.screen.detail.DetailViewModel
import id.bobipermanasandi.nachricht.ui.screen.bookmark.BookmarkViewModel
import id.bobipermanasandi.nachricht.ui.screen.home.HomeViewModel

class ViewModelFactory(private val repository: NewsRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(BookmarkViewModel::class.java)) {
            return BookmarkViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}