package com.arash.github.di.module

import com.arash.github.detail.DetailActivity
import com.arash.github.issue.IssueActivity
import com.arash.github.di.scope.PerActivity
import dagger.Module
import dagger.android.AndroidInjectionModule
import dagger.android.ContributesAndroidInjector

/**
 * Created by Arash Golmohammadi on 8/1/2019.
 */

@Module(includes = [AndroidInjectionModule::class,NetworkModule::class,ViewModelModule::class])
abstract class ActivityBuilderModule {

    @PerActivity
    @ContributesAndroidInjector(modules = [IssueListActivityModule::class])
    internal abstract fun issueListInjector(): IssueActivity

    @PerActivity
    @ContributesAndroidInjector(modules = [DetailActivityModule::class])
    internal abstract fun detailActivityInjector(): DetailActivity

}