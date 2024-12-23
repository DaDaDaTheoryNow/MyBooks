package com.dadadadev.my_books.book.presentation.book_list

import com.dadadadev.my_books.book.domain.Book
import com.dadadadev.my_books.core.presentation.UiText

data class BookListState(
    val searchQuery: String = "Kotlin",
    val searchResults: List<Book> = emptyList(),
    val favoriteBooks: List<Book> = emptyList(),
    val isLoading: Boolean = true,
    val selectedTabIndex: Int = 0,
    val errorMessage: UiText? = null
)