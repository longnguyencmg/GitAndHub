package com.tolo.app.gitandhub.ui.browse

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.tolo.app.data.model.GithubRepo
import com.tolo.app.gitandhub.R

class BrowseAdapter : RecyclerView.Adapter<BrowseAdapter.ViewHolder>() {

    var repos: List<GithubRepo> = arrayListOf()
    lateinit var itemRepoClick: ((item: GithubRepo) -> Unit)

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repo = repos[position]
        Glide.with(holder.avatar.context).load(repo.owner?.avatarUrl).into(holder.avatar)
        holder.name.text = repo.name
        holder.subName.text = repo.language
        holder.stars.text = repo.stargazersCount.toString()
        holder.updateAt.text = "Update at: ${repo.updatedAt}"

        holder.itemView.setOnClickListener { itemRepoClick.invoke(repo) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_repo, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return repos.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var avatar: ImageView = view.findViewById(R.id.avatar)
        var name: TextView = view.findViewById(R.id.name)
        var subName: TextView = view.findViewById(R.id.subName)
        var stars: TextView = view.findViewById(R.id.stars)
        var updateAt: TextView = view.findViewById(R.id.updateAt)
    }

}