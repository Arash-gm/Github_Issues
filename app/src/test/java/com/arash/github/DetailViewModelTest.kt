package com.arash.github

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.Observer
import com.arash.github.data.model.Comment
import com.arash.github.data.model.Issue
import com.arash.github.data.repository.CommentRepository
import com.arash.github.data.repository.IssueRepository
import com.arash.github.detail.CommentRowView
import com.arash.github.detail.DetailViewModel
import com.arash.github.issue.IssueViewModel
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Answers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by Arash Golmohammadi on 8/10/2019.
 */

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    private lateinit var viewModel: DetailViewModel

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    lateinit var repository: CommentRepository

    @Mock
    lateinit var commentRowView: CommentRowView

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var observer: Observer<ArrayList<Comment>>

    @Mock
    lateinit var lifecycleOwner: LifecycleOwner
    lateinit var lifecycle: Lifecycle

    @Before
    fun setUp() {
        lifecycle = LifecycleRegistry(lifecycleOwner)
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        viewModel = DetailViewModel(repository)
        viewModel.getCommentObservable().observeForever(observer)
    }

    @After
    fun tearDown() {
        viewModel.getCommentObservable().removeObserver(observer)
    }
}