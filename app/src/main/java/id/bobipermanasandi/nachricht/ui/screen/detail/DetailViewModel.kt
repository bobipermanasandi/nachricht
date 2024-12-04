package id.bobipermanasandi.nachricht.ui.screen.detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.bobipermanasandi.nachricht.data.NewsRepository
import id.bobipermanasandi.nachricht.model.News
import id.bobipermanasandi.nachricht.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: NewsRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<News>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<News>>
        get() = _uiState

    private val _message = mutableStateOf("")
    val message: State<String?> = _message

    fun getNewsById(id: Int) = viewModelScope.launch {
        _uiState.value = UiState.Loading
        _uiState.value = UiState.Success(repository.getNewsById(id))
    }

    fun updateNews(id: Int, newState: Boolean) = viewModelScope.launch {
        repository.updateNews(id, !newState)
            .collect { isUpdated ->
                if (isUpdated) getNewsById(id)
                if (!newState) {
                    _message.value = "Add to Bookmark"
                } else {
                    _message.value = "Remove from Bookmark"
                }
            }
    }
}