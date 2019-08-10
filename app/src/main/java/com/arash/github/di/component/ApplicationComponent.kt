package com.arash.github.di.component

import com.arash.github.base.ApplicationClass
import com.arash.github.di.module.ActivityBuilderModule
import com.arash.github.di.module.ApplicationModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

/**
 * Created by Arash Golmohammadi on 8/1/2019.
 */

@Singleton
@Component(modules = [AndroidInjectionModule::class, ApplicationModule::class, ActivityBuilderModule::class])
interface ApplicationComponent: AndroidInjector<ApplicationClass> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<ApplicationClass>()

    //fun inject(categoryListPresenter: CategoryListPresenter)
}