package org.example.minisystem.di

import org.example.minisystem.domain.usecases.AddProduct
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainModule = module {
    factoryOf(::AddProduct)
}