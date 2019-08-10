package com.arash.github.issue

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.arash.github.R
import com.arash.github.base.BaseActivity
import com.arash.github.data.model.Issue
import com.arash.github.data.model.State
import com.arash.github.detail.DetailActivity
import com.arash.github.util.Globals.withSchedulers
import com.arash.github.util.PaginationScrollListener
import com.jakewharton.rxbinding3.widget.textChanges
import kotlinx.android.synthetic.main.activity_issue.*
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by Arash Golmohammadi on 8/2/2019.
 */

class IssueActivity : BaseActivity(), SwipeRefreshLayout.OnRefreshListener {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject lateinit var layoutManager: LinearLayoutManager
    @Inject lateinit var adapter: IssueAdapter
    @Inject lateinit var okHttpClient: OkHttpClient

    private lateinit var viewModel: IssueViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)[IssueViewModel::class.java]
        initViews()
        observeIssues()
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.activity_issue
    }

    private fun initViews(){
        initIssueList()
        initSearchWatcher()
        initSwitchState()
    }

    private fun initIssueList() {
        rvIssues.layoutManager = layoutManager
        rvIssues.itemAnimator = DefaultItemAnimator()
        rvIssues.setHasFixedSize(true)
        pullToRefresh.setOnRefreshListener(this)
        provideListPagination()
        adapter.provideViewModel(this,viewModel)
        rvIssues.adapter = adapter
        observeIssueItemClick()
    }

    private fun observeIssueItemClick() {
        addDisposable(viewModel.getListItemClickSubject()
            .compose(withSchedulers())
            .subscribe{
                goToDetail(it)
            })
    }

    private fun goToDetail(issue: Issue){
        DetailActivity.start(this,issue)
    }

    private fun provideListPagination() {
        observePaginationLoadingState()
        rvIssues.addOnScrollListener(object : PaginationScrollListener(layoutManager) {
            override fun loadMoreItems() {
                viewModel.fetchIssuesPagination()
            }
            override fun isLoading(): Boolean {
                return viewModel.isLoadingPaginationObservable().blockingFirst()
            }
        })
    }

    private fun observePaginationLoadingState() {
        addDisposable(viewModel.isLoadingPaginationObservable()
            .compose(withSchedulers())
            .subscribe(this::showHideLoadingPagination))
    }

    private fun showHideLoadingPagination(showProgressbar: Boolean){
        when(showProgressbar){
            true -> showPaginationLoading()
            false -> hidePaginationLoading()
        }
    }

    private fun showPaginationLoading(){
        pbPaginationLoading.visibility = View.VISIBLE
    }

    private fun hidePaginationLoading(){
        pbPaginationLoading.visibility = View.GONE
    }

    private fun observeIssues(){
        observeLoadingState()
        viewModel.getIssuesObservable()
            .observe(this, Observer {
                    it?.apply{
                        loadIssueList(it)
                    }
            })
    }

    private fun observeLoadingState() {
        addDisposable(viewModel.isLoadingObservable()
            .compose(withSchedulers())
            .subscribe(this::showHideLoadingBar))
    }

    override fun onRefresh() {
        pullToRefresh.isRefreshing = false
        viewModel.pullToRefresh()
    }

    private fun loadIssueList(issues: ArrayList<Issue>){
        issues?.let {
            adapter.refreshList()
        }
    }

    private fun showHideLoadingBar(showLoadingBar: Boolean){
        when(showLoadingBar){
            true -> {
                hideIssueList()
                pbIssuesLoading.show()
            }
            false -> {
                pbIssuesLoading.hide()
                showIssueList()
            }
        }
    }

    private fun hideIssueList(){
        rvIssues.visibility = View.GONE
    }

    private fun showIssueList(){
        rvIssues.visibility = View.VISIBLE
    }

    private fun initSearchWatcher(){
        addDisposable(edtSearch.textChanges()
            .filter{t -> t.length > 2}
            .map{it.toString()}
            .debounce(500, TimeUnit.MILLISECONDS)
            .compose(withSchedulers())
            .subscribe{
                viewModel.filter.filter(it)
            })

        addDisposable(edtSearch.textChanges()
            .filter{t -> t.length <= 2}
            .map{it.toString()}
            .debounce(500, TimeUnit.MILLISECONDS)
            .compose(withSchedulers())
            .subscribe{
                viewModel.toOriginalResult()
            })
    }

    private fun initSwitchState(){
        switchIcon.setOnClickListener {
            switchIcon.switchState(true)
            if(!switchIcon.isIconEnabled){
                viewModel.fetchIssues(State.CLOSED)
            }else{
                viewModel.fetchIssues(State.OPEN)
            }
        }
    }

    fun getHttpClient(): OkHttpClient {
        return okHttpClient
    }
}

