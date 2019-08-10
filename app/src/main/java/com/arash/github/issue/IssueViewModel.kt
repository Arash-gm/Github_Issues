package com.arash.github.issue

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import com.arash.github.data.model.Issue
import com.arash.github.data.model.IssueFilter
import com.arash.github.data.model.State
import com.arash.github.data.repository.IssueRepository
import com.arash.github.util.Globals.withSchedulers
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

/**
 * Created by Arash Golmohammadi on 8/2/2019.
 */

class IssueViewModel @Inject constructor(private val issueRepository: IssueRepository) : ViewModel(),Filterable {

    private val issues = arrayListOf<Issue>()
    private val itemClickSubject = PublishSubject.create<Issue>()
    private val isLoadingSubject = BehaviorSubject.create<Boolean>()
    private val isLoadingPaginationSubject = BehaviorSubject.create<Boolean>()
    private var currentPage = 1
    private var currentFilter: IssueFilter = IssueFilter(state = State.OPEN.state, page = currentPage)
    private val issueLiveData: MutableLiveData<ArrayList<Issue>> = MutableLiveData()
    private val disposable: CompositeDisposable = CompositeDisposable()
    private var originalList: ArrayList<Issue> = arrayListOf()

    init {
        fetchIssues()
    }

    private fun fetchIssues(){
        isLoadingSubject.onNext(true)
        return fetchIssues(currentFilter)
    }

    fun fetchIssues(state: State){
        currentFilter.state = state.state
        resettingData()
        fetchIssues()
    }

    private fun fetchIssues(hitsFilter: IssueFilter) {
        currentFilter = hitsFilter
        disposable.add(issueRepository.getIssues(currentFilter).compose(withSchedulers()).doOnEach{
            isLoadingSubject.onNext(false)
            isLoadingPaginationSubject.onNext(false)
        }
            .subscribe{
                issueLiveData.value = it
                issues.addAll(it)
                originalList.clear()
                originalList.addAll(it)
            }
        )
    }

    fun fetchIssuesPagination() {
        isLoadingPaginationSubject.onNext(true)
        increasePageNumber()
        fetchIssues(currentFilter)
    }

    fun onBindRowViewAtPosition(position: Int, rowView: IssueRowView) {
        var issue = issues[position]
        rowView.apply{
            setTitle(issue.title)
            setCreatedAt(issue.createdAt)
            setUser(issue.user.userName)
            setState(issue.state)
        }
    }

    fun getListRowsCount(): Int {
        return issues.size
    }

    fun onClickIssueItem(adapterPosition: Int) {
        itemClickSubject.onNext(issues[adapterPosition])
    }

    fun pullToRefresh() {
        resetPageNumber()
        issues.clear()
        return fetchIssues()
    }

    private fun resetPageNumber(){
        currentPage = 1
        currentFilter.page = currentPage
    }

    fun getIssuesObservable(): MutableLiveData<ArrayList<Issue>>{
        return issueLiveData
    }

    fun isLoadingObservable(): Observable<Boolean> {
        return isLoadingSubject
    }

    fun isLoadingPaginationObservable(): Observable<Boolean> {
        return isLoadingPaginationSubject
    }

    override fun onCleared() {
        super.onCleared()
        if(!disposable.isDisposed){
            disposable.dispose()
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                issues.clear()
                issues.addAll(results.values as ArrayList<Issue>)
                issueLiveData.value = issues
            }
            override fun performFiltering(constraint: CharSequence): FilterResults {
                var constraint = constraint.toString().toLowerCase()
                val results = FilterResults()
                val filteredArrayNames = ArrayList<Issue>()
                val filteredList = issues
                for (i in filteredList.indices) {
                    val issue = filteredList[i]
                    if (issue.title.toLowerCase().contains(constraint) || issue.body.toLowerCase().contains(constraint)){
                        filteredArrayNames.add(issue)
                    }
                }
                results.count = filteredArrayNames.size
                results.values = filteredArrayNames
                return results
            }
        }
    }

    fun toOriginalResult(){
        if(originalList.size > 0){
            reloadingData()
        }
    }

    private fun reloadingData(){
        issues.clear()
        issues.addAll(originalList)
        issueLiveData.value = issues
    }

    private fun resettingData(){
        issues.clear()
        originalList.clear()
        issueLiveData.value = issues
    }

    private fun increasePageNumber(){
        currentPage++
        currentFilter.page = currentPage
    }

    fun getListItemClickSubject(): Observable<Issue>{
        return itemClickSubject
    }

    fun getIssues(): ArrayList<Issue> {
        return issues
    }
}