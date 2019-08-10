package com.arash.github.detail

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.arash.github.R
import com.arash.github.base.BaseActivity
import com.arash.github.data.model.Comment
import com.arash.github.data.model.Issue
import com.arash.github.data.model.State
import com.arash.github.util.Globals
import com.arash.github.util.Globals.BUNDLE_ISSUE_DETAIL
import kotlinx.android.synthetic.main.activity_detail.*
import okhttp3.OkHttpClient
import javax.inject.Inject

/**
 * Created by Arash Golmohammadi on 8/7/2019.
 */

class DetailActivity: BaseActivity(){

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: DetailViewModel
    @Inject lateinit var layoutManager: LinearLayoutManager
    @Inject lateinit var adapter: CommentAdapter
    @Inject lateinit var okHttpClient: OkHttpClient

    companion object {
        fun start(activityContext: Activity, issue: Issue) {
            var intent = Intent(activityContext,DetailActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            intent.putExtra(BUNDLE_ISSUE_DETAIL,issue)
            activityContext.startActivity(intent)
        }
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.activity_detail
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)[DetailViewModel::class.java]
        intent?.let {
            handleIntent(savedInstanceState,it)
        }
    }

    private fun handleIntent(savedInstanceState: Bundle?,intent: Intent){
        intent.extras?.let {
            val issue = it.getParcelable(BUNDLE_ISSUE_DETAIL) as Issue
            if(savedInstanceState == null){
                viewModel.setSelectedIssue(issue)
            }
            initViews(issue)
            observeComments()
        }
    }

    private fun initViews(issue: Issue){
        with(issue){
            tvDetailTitle.text = title
            tvDetailBody.loadMarkdown(body)

            tvDetailState.text = state
            when(state){
                State.OPEN.state -> tvDetailState.setTextColor(ContextCompat.getColor(this@DetailActivity,R.color.colorGreen))
                State.CLOSED.state -> tvDetailState.setTextColor(ContextCompat.getColor(this@DetailActivity,R.color.colorAccent))
            }
            tvDetailAuthor.text = user.userName
            tvDetailCreatedAt.text = createdAt.substring(0,10)
        }

        initCommentsList()
    }

    private fun initCommentsList(){
        rvComments.layoutManager = layoutManager
        rvComments.itemAnimator = DefaultItemAnimator()
        adapter.provideViewModel(this,viewModel)
        rvComments.adapter = adapter
    }

    private fun observeComments(){
        observeLoadingState()
        viewModel.getCommentObservable()
            .observe(this, Observer {
                it?.apply{
                    loadCommentList(it)
                }
            })
    }

    private fun observeLoadingState() {
        addDisposable(viewModel.isLoadingObservable()
            .compose(Globals.withSchedulers())
            .subscribe(this::showHideLoading))
    }

    private fun showHideLoading(showLoadingBar: Boolean){
        when(showLoadingBar){
            true -> {
                hideCommentList()
                pbCommentLoading.show()
            }
            false -> {
                pbCommentLoading.hide()
                showCommentList()
            }
        }
    }

    private fun hideCommentList(){
        rvComments.visibility = View.GONE
    }

    private fun showCommentList(){
        rvComments.visibility = View.VISIBLE
    }

    private fun loadCommentList(issues: ArrayList<Comment>){
        issues?.let {
            adapter.refreshList()
        }
    }

    fun getHttpClient(): OkHttpClient {
        return okHttpClient
    }
}