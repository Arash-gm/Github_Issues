package com.arash.github.detail

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arash.github.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.comment_list_row.view.*
import javax.inject.Inject

/**
 * Created by Arash Golmohammadi on 8/9/2019.
 */

class CommentAdapter @Inject constructor(): RecyclerView.Adapter<CommentAdapter.ItemViewHolder>() {

    private lateinit var viewModel: DetailViewModel
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ItemViewHolder{
        val view = LayoutInflater.from(parent.context).inflate(R.layout.comment_list_row, parent, false)

        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        viewModel.onBindRowViewAtPosition(position, holder)
    }

    fun provideViewModel(context: Context, viewModel: DetailViewModel){
        this.viewModel = viewModel
        this.context = context
    }

    override fun getItemCount(): Int {
        return viewModel.getListRowsCount()
    }

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view), CommentRowView {
        override fun setUserName(userName: String) {
            itemView.tvUserName.text = userName
        }

        override fun setBody(body: String) {
            itemView.tvCommentBody.loadMarkdown(body)
        }

        override fun loadAvatar(avatarUrl: String) {
            val requestOption: RequestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).error(R.drawable.ic_no_image).placeholder(
                R.drawable.ic_placeholder)
            Glide.with(context).load(avatarUrl).apply(requestOption).into(itemView.imgUserAvatar)
        }
    }

    fun refreshList(){
        notifyDataSetChanged()
    }
}