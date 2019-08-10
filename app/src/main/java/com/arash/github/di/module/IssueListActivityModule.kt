package com.arash.github.di.module

import android.support.v7.widget.LinearLayoutManager
import com.arash.github.base.ApplicationClass
import com.arash.github.data.api.ApiCall
import com.arash.github.issue.IssueActivity
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjectionModule
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created by Arash Golmohammadi on 8/1/2019.
 */

@Module(includes = [AndroidInjectionModule::class])
class IssueListActivityModule {

    @Provides
    fun provideLayoutManager(activity: IssueActivity): LinearLayoutManager {
        return LinearLayoutManager(activity)
    }
}