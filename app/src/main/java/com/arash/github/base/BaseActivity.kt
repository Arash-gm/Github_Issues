package com.arash.github.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import dagger.android.AndroidInjection
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by Arash Golmohammadi on 8/1/2019.
 */

abstract class BaseActivity: AppCompatActivity() {

    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResourceId())
    }

    protected abstract fun getLayoutResourceId(): Int

    private fun clearDisposable() {
        disposables.clear()
    }

    protected fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

    override fun onDestroy() {
        super.onDestroy()
        clearDisposable()
    }
}