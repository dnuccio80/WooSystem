package org.example.minisystem.di

import org.example.minisystem.data.db.repositoriesimpl.ProductRepositoryImpl
import org.example.minisystem.domain.repositories.ProductRepository
import org.koin.dsl.module

val dataModule = module {
    single<ProductRepository> { ProductRepositoryImpl(get()) }
}