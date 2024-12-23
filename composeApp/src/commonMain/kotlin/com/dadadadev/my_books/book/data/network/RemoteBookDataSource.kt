package com.dadadadev.my_books.book.data.network

import com.dadadadev.my_books.book.data.dto.SearchResponseDto
import com.dadadadev.my_books.core.domain.DataError
import com.dadadadev.my_books.core.domain.Result

interface RemoteBookDataSource {
    suspend fun searchBooks(
        query: String,
        resultLimit: Int? = null
    ): Result<SearchResponseDto, DataError.Remote>
}