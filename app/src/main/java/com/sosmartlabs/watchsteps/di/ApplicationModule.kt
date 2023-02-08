package com.sosmartlabs.watchfriends.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

/**
 * Module that manages the dependency injection of coroutine-related components
 */
@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    /**
     * Injects an external scope for coroutines
     */
    @Singleton
    @Provides
    fun providesExternalCoroutineScope(): CoroutineScope {
        return GlobalScope
    }

    /**
     * Injects a CoroutineContext for IO operations
     */
    @Singleton
    @Provides
    fun providesIoCoroutineContext(): CoroutineContext {
        return Dispatchers.IO
    }

}