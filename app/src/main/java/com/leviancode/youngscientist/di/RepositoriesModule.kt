package com.leviancode.youngscientist.di

import com.leviancode.youngscientist.data.repositories.JournalsRepository
import com.leviancode.youngscientist.data.repositories.impl.JournalsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesModule {

    @Binds
    abstract fun bindJournalRepository(
        journalsRepositoryImpl: JournalsRepositoryImpl
    ): JournalsRepository
}