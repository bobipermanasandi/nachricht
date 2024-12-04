package id.bobipermanasandi.nachricht.ui.screen.bookmark

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
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

    private val _message = mutableStateOf("")
    val message: State<String?> = _message

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
        if (newState) {
            _message.value = "Add to Bookmark"
        } else {
            _message.value = "Remove from Bookmark"
        }

        getBookmarkNews()
    }
}