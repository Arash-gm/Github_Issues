package com.arash.github.issue

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import com.arash.github.R
import com.arash.github.data.model.State
import kotlinx.android.synthetic.main.issue_list_row.view.*
import javax.inject.Inject

/**
 * Created by Arash Golmohammadi on 8/2/2019.
 */

class IssueAdapter @Inject constructor(): RecyclerView.Adapter<IssueAdapter.ItemViewHolder>() {

    private lateinit var viewModel: IssueViewModel
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ItemViewHolder{
        val view = LayoutInflater.from(parent.context).inflate(R.layout.issue_list_row, parent, false)
        val holder = ItemViewHolder(view)
        view.setOnClickListener {
            viewModel?.let {
                viewModel.onClickIssueItem(holder.adapterPosition)
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        viewModel.onBindRowViewAtPosition(position, holder)
    }

    fun provideViewModel(context: Context, viewModel: IssueViewModel){
        this.viewModel = viewModel
        this.context = context
    }

    override fun getItemCount(): Int {
        return viewModel.getListRowsCount()
    }

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view), IssueRowView {
        override fun setTitle(title: String) {
            itemView.tvTitle.text = title
        }

        override fun setCreatedAt(createdAt: String) {
            itemView.tvCreatedAt.text = createdAt.substring(0,10)
        }

        override fun setUser(user: String) {
            itemView.tvUser.text = context.getString(R.string.by) + " " + user + " " + context.getString(R.string.at)
        }

        override fun setState(state: String) {
            itemView.tvIssueState.text = state

            when(state){
                State.OPEN.state -> itemView.tvIssueState.setTextColor(ContextCompat.getColor(context,R.color.colorGreen))
                State.CLOSED.state -> itemView.tvIssueState.setTextColor(ContextCompat.getColor(context,R.color.colorAccent))
            }
        }
    }

    fun refreshList(){
        notifyDataSetChanged()
    }
}