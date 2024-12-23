package com.dadadadev.my_books

import androidx.compose.ui.window.ComposeUIViewController
import com.dadadadev.my_books.app.App
import com.dadadadev.my_books.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = { initKoin() }
) { App() }