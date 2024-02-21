package com.berdimyradov.news.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.berdimyradov.news.domain.model.Category
import com.berdimyradov.news.domain.use_case.GetNewsUseCase
import com.berdimyradov.news.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase
) : ViewModel() {

    private val _newsResponse = MutableStateFlow<Resource<List<Category>>?>(null)
    val newsResponse = _newsResponse.asStateFlow()

    fun getNews() = viewModelScope.launch {
        getNewsUseCase().onEach(_newsResponse::emit).launchIn(viewModelScope)
    }
}