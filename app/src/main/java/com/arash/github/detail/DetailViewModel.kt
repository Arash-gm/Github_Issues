package com.arash.github.detail

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.arash.github.data.model.Comment
import com.arash.github.data.model.Issue
import com.arash.github.data.repository.CommentRepository
import com.arash.github.util.Globals
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

/**
 * Created by Arash Golmohammadi on 8/7/2019.
 */

class DetailViewModel @Inject constructor(private val commentRepository: CommentRepository) : ViewModel(){

    private val comments = arrayListOf<Comment>()
    private lateinit var selectedIssue: Issue
    private val disposable: CompositeDisposable = CompositeDisposable()
    private val isLoadingSubject = BehaviorSubject.create<Boolean>()
    private val commentsLiveData: MutableLiveData<ArrayList<Comment>> = MutableLiveData()

    fun setSelectedIssue(issue: Issue) {
        selectedIssue = issue
        fetchComments()
    }

    private fun fetchComments(){
        isLoadingSubject.onNext(true)

        disposable.add(commentRepository.getComments(selectedIssue.commentsUrl).compose(Globals.withSchedulers()).doOnEach{
            isLoadingSubject.onNext(false)
        }
            .subscribe{
                commentsLiveData.value = it
                comments.addAll(it)
            }
        )
    }

    override fun onCleared() {
        super.onCleared()
        if(!disposable.isDisposed){
            disposable.dispose()
        }
    }

    fun getCommentObservable(): MutableLiveData<ArrayList<Comment>>{
        return commentsLiveData
    }

    fun onBindRowViewAtPosition(position: Int, rowView: CommentRowView) {
        var comment = comments[position]
        rowView.apply{
            setUserName(comment.user.userName)
            setBody(comment.body)
            loadAvatar(comment.user.avatarUrl)
        }
    }

    fun getListRowsCount(): Int {
        return comments.size
    }

    fun isLoadingObservable(): Observable<Boolean> {
        return isLoadingSubject
    }
}