package com.arash.github

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.Observer
import com.arash.github.data.model.Issue
import com.arash.github.data.model.IssueFilter
import com.arash.github.data.model.User
import com.arash.github.data.repository.IssueRepository
import com.arash.github.issue.IssueRowView
import com.arash.github.issue.IssueViewModel
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Observable
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.plugins.RxAndroidPlugins
import org.mockito.*
import org.mockito.Mock
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
import io.reactivex.observers.TestObserver
import org.junit.*
import org.junit.Assert.*
import org.mockito.Mockito.`when`

/**
 * Created by Arash Golmohammadi on 8/9/2019.
 */

@RunWith(MockitoJUnitRunner::class)
class IssueViewModelTest {

    private lateinit var viewModel: IssueViewModel
    private var currentPage = 1

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    lateinit var repository: IssueRepository

    @Mock
    lateinit var issueRowView: IssueRowView

    @Mock
    lateinit var observer: Observer<ArrayList<Issue>>

    @Mock
    lateinit var lifecycleOwner: LifecycleOwner
    lateinit var lifecycle: Lifecycle

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val currentFilter: IssueFilter = IssueFilter(page = currentPage)
    private val openIssue: Issue = Issue(state = "open",user = User(id = "1",userName = "Arash-gm",avatarUrl = "")
        ,title = "Issue Number 1", body = "Body of the issue",commentsUrl = "", createdAt = "8/9/2019")

    @Before
    fun setUp() {
        lifecycle = LifecycleRegistry(lifecycleOwner)
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        `when`(repository.getIssues(currentFilter)).thenReturn(
            Observable.create { arrayListOf(openIssue) }
        )
        viewModel = IssueViewModel(repository)
        viewModel.getIssues().add(openIssue)
        viewModel.getIssuesObservable().observeForever(observer)
    }

    @After
    fun tearDown() {
        viewModel.getIssuesObservable().removeObserver(observer)
    }

    @Test
    fun testLiveData() {
        assertNotNull(viewModel.getIssuesObservable())
        assertTrue(viewModel.getIssuesObservable().hasObservers())
    }


    @Test
    fun adapterBinding(){
        viewModel.onBindRowViewAtPosition(0,issueRowView)
        verify(issueRowView).setTitle(openIssue.title)
        verify(issueRowView).setCreatedAt(openIssue.createdAt)
        verify(issueRowView).setState(openIssue.state)
        verify(issueRowView).setUser(openIssue.user.userName)
    }

    @Test
    fun adapterCount(){
        Assert.assertNotNull(viewModel.getListRowsCount())
    }

    @Test
    fun onClickHitItem(){
        viewModel.onClickIssueItem(0)
        assertNotNull(viewModel.getListItemClickSubject())
        val observer = TestObserver.create<Issue>()
        viewModel.getListItemClickSubject().subscribe(observer)
        observer.assertNoErrors()
        observer.hasSubscription()
    }

    @Test
    fun issueLoadingTest(){
        val observer = TestObserver.create<Boolean>()
        viewModel.isLoadingObservable().subscribe(observer)
        observer.assertValue {true}
    }

    @Test
    fun fetchIssuePagination(){
        viewModel.fetchIssuesPagination()
        val observer = TestObserver.create<Boolean>()
        viewModel.isLoadingPaginationObservable().subscribe(observer)
        observer.assertValue { true}
    }
}