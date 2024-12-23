package com.dadadadev.my_books.book.presentation.book_detail

import com.dadadadev.my_books.book.domain.Book

data class BookDetailState(
    val isLoading: Boolean = true,
    val isFavorite: Boolean = false,
    val book: Book? = null
)
