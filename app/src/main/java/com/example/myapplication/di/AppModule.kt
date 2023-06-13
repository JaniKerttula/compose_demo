package com.example.myapplication.di

import com.example.myapplication.ExampleRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideExampleRepository(): ExampleRepository = ExampleRepository()

    // You can also initialize Services for API calls here or some other module, using Retrofit for example
}