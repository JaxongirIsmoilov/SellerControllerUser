package uz.gita.jaxongir.sellermanageruser.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import uz.gita.jaxongir.sellermanageruser.presenter.login.LoginDirection
import uz.gita.jaxongir.sellermanageruser.presenter.login.LoginDirectionImpl
import uz.gita.jaxongir.sellermanageruser.presenter.splash.SplashDirection
import uz.gita.jaxongir.sellermanageruser.presenter.splash.SplashDirectionImpl
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
interface DirectionModule {
    @Binds
    fun bindsSplashDirection(impl: SplashDirectionImpl): SplashDirection

    @Binds
    fun bindsLoginDirection(impl : LoginDirectionImpl) : LoginDirection

}