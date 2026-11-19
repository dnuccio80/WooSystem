package org.example.minisystem.di

import org.example.minisystem.ui.products.ProductsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val uiModule = module {
    viewModelOf(::ProductsViewModel)
}