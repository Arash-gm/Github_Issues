package com.arash.github.di.module

import android.app.Application
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.arash.github.base.ApplicationClass
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjectionModule

/**
 * Created by Arash Golmohammadi on 8/1/2019.
 */

@Module(includes = [AndroidInjectionModule::class])
abstract class ApplicationModule{

    @Binds
    internal abstract fun application(linearLayoutManager: LinearLayoutManager): RecyclerView.LayoutManager
}