package id.bobipermanasandi.nachricht.ui.screen.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.bobipermanasandi.nachricht.data.NewsRepository
import id.bobipermanasandi.nachricht.model.News
import id.bobipermanasandi.nachricht.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class BookmarkViewModel(private val repository: NewsRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<News>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<News>>>
        get() = _uiState

    fun getBookmarkNews() = viewModelScope.launch {
        repository.getBookmarkNews()
            .catch {
                _uiState.value = UiState.Error(it.message.toString())
            }
            .collect {
                _uiState.value = UiState.Success(it)
            }
    }

    fun updateNews(id: Int, newState: Boolean) {
        repository.updateNews(id, newState)

        getBookmarkNews()
    }
}