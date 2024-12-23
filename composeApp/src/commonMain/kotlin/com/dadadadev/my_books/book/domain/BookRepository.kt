package com.dadadadev.my_books.book.domain

import com.dadadadev.my_books.core.domain.DataError
import com.dadadadev.my_books.core.domain.Result

interface BookRepository {
    suspend fun searchBooks(query: String): Result<List<Book>, DataError.Remote>
}