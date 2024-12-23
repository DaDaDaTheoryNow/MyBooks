package com.dadadadev.my_books.di

import com.dadadadev.my_books.book.data.network.KtorRemoteBookDataSource
import com.dadadadev.my_books.book.data.network.RemoteBookDataSource
import com.dadadadev.my_books.book.data.repository.DefaultBookRepository
import com.dadadadev.my_books.book.domain.BookRepository
import com.dadadadev.my_books.book.presentation.SelectedBookViewModel
import com.dadadadev.my_books.book.presentation.book_detail.BookDetailViewModel
import com.dadadadev.my_books.book.presentation.book_list.BookListViewModel
import com.dadadadev.my_books.core.data.HttpClientFactory
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    single { HttpClientFactory.create(get()) }
    singleOf(::KtorRemoteBookDataSource).bind<RemoteBookDataSource>()
    singleOf(::DefaultBookRepository).bind<BookRepository>()

    viewModelOf(::BookListViewModel)
    viewModelOf(::BookDetailViewModel)
    viewModelOf(::SelectedBookViewModel)
}