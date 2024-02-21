package com.berdimyradov.news.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.berdimyradov.news.domain.model.Category
import com.berdimyradov.news.domain.use_case.GetNewsUseCase
import com.berdimyradov.news.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase
) : ViewModel() {

    private var _searchJob: Job? = null

    private var currentData = listOf<Category>()

    private val _newsResponse = MutableStateFlow<Resource<List<Category>>?>(null)
    val newsResponse = _newsResponse.asStateFlow()

    fun getNews() = viewModelScope.launch {
        getNewsUseCase().onEach { resource ->
            when (resource) {
                is Resource.Error -> _newsResponse.emit(resource)
                is Resource.Loading -> {
                    currentData = resource.data ?: emptyList()
                    _newsResponse.emit(resource)
                }
                is Resource.Success -> {
                    currentData = resource.data ?: emptyList()
                    _newsResponse.emit(resource)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun searchNews(query: String) {
        _searchJob?.cancel()
        _searchJob = viewModelScope.launch(Dispatchers.IO) {
            delay(500L)
            if (query.isBlank()){
                _newsResponse.emit(Resource.Success(currentData))
                return@launch
            }
            val newData = currentData.map { category ->
                category.copy(articles = category.articles.filter { it.doesMatchSearchQuery(query) })
            }
            _newsResponse.emit(Resource.Success(newData))
        }
    }
}