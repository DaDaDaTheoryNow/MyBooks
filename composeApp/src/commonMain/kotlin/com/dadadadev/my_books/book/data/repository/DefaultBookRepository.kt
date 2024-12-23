package com.dadadadev.my_books.book.data.repository

import com.dadadadev.my_books.book.data.mappers.toBook
import com.dadadadev.my_books.book.data.network.RemoteBookDataSource
import com.dadadadev.my_books.book.domain.Book
import com.dadadadev.my_books.book.domain.BookRepository
import com.dadadadev.my_books.core.domain.DataError
import com.dadadadev.my_books.core.domain.Result
import com.dadadadev.my_books.core.domain.map

class DefaultBookRepository(
    private val remoteBookDataSource: RemoteBookDataSource
): BookRepository {
    override suspend fun searchBooks(query: String): Result<List<Book>, DataError.Remote> {
        return remoteBookDataSource
            .searchBooks(query)
            .map { dto ->
                dto.results.map { it.toBook() }
            }
    }
}