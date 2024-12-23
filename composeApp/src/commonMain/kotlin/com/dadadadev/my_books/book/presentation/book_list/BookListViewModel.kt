package com.dadadadev.my_books.book.presentation.book_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dadadadev.my_books.book.domain.Book
import com.dadadadev.my_books.book.domain.BookRepository
import com.dadadadev.my_books.core.domain.onError
import com.dadadadev.my_books.core.domain.onSuccess
import com.dadadadev.my_books.core.presentation.toUiText
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class BookListViewModel(
    private val dataSource: BookRepository
) : ViewModel() {
    private var cachedBooks = emptyList<Book>()
    private var searchJob: Job? = null

    private val _state = MutableStateFlow(BookListState())
    val state = _state
        .onStart {
            if (cachedBooks.isEmpty()) {
                observeSearchQuery()
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _state.value
        )

    fun onAction(action: BookListAction) {
        when (action) {
            is BookListAction.OnBookClick -> Unit

            is BookListAction.OnSearchQueryChange -> _state.update {
                it.copy(searchQuery = action.query)
            }

            is BookListAction.OnTabSelected -> _state.update {
                it.copy(selectedTabIndex = action.index)
            }
        }
    }

    private fun observeSearchQuery() {
        state
            .map { it.searchQuery }
            .distinctUntilChanged()
            .debounce(500)
            .onEach { query ->
                when {
                    query.isBlank() -> {
                        _state.update { it.copy(
                            errorMessage = null,
                            searchResults = cachedBooks,
                        ) }
                    }
                    query.length >= 2 -> {
                        searchJob?.cancel()
                        searchJob = searchBooks(query)
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun searchBooks(query: String) = viewModelScope.launch {
        _state.update { it.copy(
            isLoading = true,
        ) }
        dataSource
            .searchBooks(query)
            .onSuccess { searchResults ->
                if (searchResults.isNotEmpty()) {
                    cachedBooks = searchResults
                }

                _state.update { it.copy(
                    searchResults = searchResults,
                    isLoading = false,
                    errorMessage = null,
                ) }
            }
            .onError { error ->
                _state.update { it.copy(
                    searchResults = emptyList(),
                    isLoading = false,
                    errorMessage = error.toUiText(),
                ) }
            }
    }
}