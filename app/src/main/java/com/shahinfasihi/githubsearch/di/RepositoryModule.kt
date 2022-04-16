package com.shahinfasihi.githubsearch.di

import com.shahinfasihi.githubsearch.data.repository.GithubRepositoryImpl
import com.shahinfasihi.githubsearch.domain.repository.GithubRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindStockRepository(
        githubRepositoryImpl: GithubRepositoryImpl
    ): GithubRepository
}