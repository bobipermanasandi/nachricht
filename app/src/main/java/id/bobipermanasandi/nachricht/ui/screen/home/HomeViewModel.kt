package id.bobipermanasandi.nachricht.ui.screen.home

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

class HomeViewModel(private val repository: NewsRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<News>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<News>>>
        get() = _uiState

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    private val _message = mutableStateOf("")
    val message: State<String?> = _message

    fun searchNews(q: String) = viewModelScope.launch {
        _query.value = q

        repository.searchNews(_query.value)
            .catch {
            _uiState.value = UiState.Error(it.message.toString())
            }
            .collect {
                _uiState.value = UiState.Success(it)
            }
    }

    fun updateNews(id: Int, newState: Boolean) = viewModelScope.launch {
        repository.updateNews(id, newState)
            .collect { isUpdated ->
                if (isUpdated) searchNews(_query.value)
                if (newState) {
                    _message.value = "Add to Bookmark"
                } else {
                    _message.value = "Remove from Bookmark"
                }
            }
    }
}