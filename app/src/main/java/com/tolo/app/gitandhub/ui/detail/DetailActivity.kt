package com.tolo.app.gitandhub.ui.detail

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.bumptech.glide.Glide
import com.tolo.app.data.model.GithubPullRequest
import com.tolo.app.data.model.GithubRepo
import com.tolo.app.gitandhub.R
import com.tolo.app.gitandhub.ui.widget.empty.EmptyListener
import com.tolo.app.gitandhub.ui.widget.error.ErrorListener
import kotlinx.android.synthetic.main.activity_browse.progress
import kotlinx.android.synthetic.main.activity_browse.recycler_browse
import kotlinx.android.synthetic.main.activity_browse.view_empty
import kotlinx.android.synthetic.main.activity_browse.view_error
import kotlinx.android.synthetic.main.activity_detail.*
import org.koin.android.ext.android.inject
import org.koin.android.scope.ext.android.bindScope
import org.koin.android.scope.ext.android.getCurrentScope
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class DetailActivity : AppCompatActivity() {

    private val detailAdapter: DetailAdapter by inject()
    private val viewModel: DetailGithubRepoViewModel by viewModel()
    lateinit var repo: GithubRepo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        repo = intent.getSerializableExtra(REPO) as GithubRepo
        bindScope(getCurrentScope())

        setupDetailView()
        setupBrowseRecycler()
        setupViewListeners()

        viewModel.getPullRequests().observe(this,
            Observer<DetailState> {
                if (it != null) this.handleDataState(it)
            })
        viewModel.fetchPullRequests(repo.owner!!.login, repo.name!!)
    }

    @SuppressLint("SetTextI18n")
    private fun setupDetailView() {
        Glide.with(this).load(repo.owner?.avatarUrl).into(avatar)
        name.text = repo.name
        subName.text = repo.language
        stars.text = repo.stargazersCount.toString()
        watchers.text = repo.watchersCount.toString()
        forks.text = repo.forksCount.toString()
        updateAt.text = "Update at: ${repo.updatedAt}"
    }

    private fun setupBrowseRecycler() {
        recycler_browse.layoutManager = LinearLayoutManager(this)
        recycler_browse.adapter = detailAdapter
    }

    private fun handleDataState(browseState: DetailState) {
        when (browseState) {
            is DetailState.Loading -> setupScreenForLoadingState()
            is DetailState.Success -> setupScreenForSuccess(browseState.data)
            is DetailState.Error -> setupScreenForError(browseState.errorMessage)
        }
    }

    private fun setupScreenForLoadingState() {
        progress.visibility = View.VISIBLE
        recycler_browse.visibility = View.GONE
        view_empty.visibility = View.GONE
        view_error.visibility = View.GONE
    }

    private fun setupScreenForSuccess(data: List<GithubPullRequest>?) {
        view_error.visibility = View.GONE
        progress.visibility = View.GONE
        if (data != null && data.isNotEmpty()) {
            updateListView(data)
            recycler_browse.visibility = View.VISIBLE
        } else {
            view_empty.visibility = View.VISIBLE
        }
    }

    private fun updateListView(repos: List<GithubPullRequest>) {
        detailAdapter.pullRequests = repos
        detailAdapter.notifyDataSetChanged()
        detailAdapter.itemRepoClick = {
            Timber.i(it.toString())
        }
    }

    private fun setupScreenForError(message: String?) {
        progress.visibility = View.GONE
        recycler_browse.visibility = View.GONE
        view_empty.visibility = View.GONE
        view_error.visibility = View.VISIBLE
    }

    private fun setupViewListeners() {
        view_empty.emptyListener = emptyListener
        view_error.errorListener = errorListener
    }

    private val emptyListener = object : EmptyListener {
        override fun onCheckAgainClicked() {
            viewModel.fetchPullRequests(repo.owner!!.login, repo.name!!)
        }
    }

    private val errorListener = object : ErrorListener {
        override fun onTryAgainClicked() {
            viewModel.fetchPullRequests(repo.owner!!.login, repo.name!!)
        }
    }

    companion object {
        const val REPO = "repo"

        fun callingIntent(context: Context, repo: GithubRepo): Intent {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(REPO, repo)
            return intent
        }
    }

}