package com.dadadadev.my_books

import android.app.Application
import com.dadadadev.my_books.di.initKoin
import org.koin.android.ext.koin.androidContext

class MyBooksApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@MyBooksApplication)
        }
    }
}