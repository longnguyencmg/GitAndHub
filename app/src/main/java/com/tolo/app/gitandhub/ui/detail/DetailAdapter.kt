package com.tolo.app.gitandhub.ui.detail

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.tolo.app.data.model.GithubPullRequest
import com.tolo.app.gitandhub.R

class DetailAdapter : RecyclerView.Adapter<DetailAdapter.ViewHolder>() {

    var pullRequests: List<GithubPullRequest> = arrayListOf()
    lateinit var itemRepoClick: ((item: GithubPullRequest) -> Unit)

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pullRequest = pullRequests[position]
        holder.number.text = pullRequest.number.toString()
        holder.title.text = pullRequest.title
        holder.title.isSelected = true
        holder.subName.text = pullRequest.user.login
        holder.status.text = pullRequest.state.toUpperCase()
        holder.updateAt.text = "Update At: ${pullRequest.updateAt}"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_pull_request, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return pullRequests.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var number: TextView = view.findViewById(R.id.number)
        var title: TextView = view.findViewById(R.id.title)
        var subName: TextView = view.findViewById(R.id.subName)
        var status: TextView = view.findViewById(R.id.status)
        var updateAt: TextView = view.findViewById(R.id.updateAt)
    }

}