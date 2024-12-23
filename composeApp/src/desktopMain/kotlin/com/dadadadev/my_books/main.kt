package com.dadadadev.my_books

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.dadadadev.my_books.app.App
import com.dadadadev.my_books.di.initKoin

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "MyBooks",
        ) {
            App()
        }
    }
}