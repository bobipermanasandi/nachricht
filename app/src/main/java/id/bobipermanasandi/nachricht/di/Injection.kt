package id.bobipermanasandi.nachricht.di

import id.bobipermanasandi.nachricht.data.NewsRepository

object Injection {
    fun provideRepository(): NewsRepository {
        return NewsRepository.getInstance()
    }
}