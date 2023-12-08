package uz.gita.jaxongir.sellermanageruser.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.jaxongir.sellermanageruser.data.repository.AppRepositoryImpl
import uz.gita.jaxongir.sellermanageruser.domain.repository.AppRepository
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {


    @[Binds Singleton]
    fun bindRepo(impl: AppRepositoryImpl): AppRepository
}