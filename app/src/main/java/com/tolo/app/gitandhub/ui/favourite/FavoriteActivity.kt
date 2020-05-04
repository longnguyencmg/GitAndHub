package com.tolo.app.gitandhub.ui.favourite

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.View
import com.tolo.app.data.model.GithubRepo
import com.tolo.app.gitandhub.R
import com.tolo.app.gitandhub.ui.detail.DetailActivity
import com.tolo.app.gitandhub.ui.widget.empty.EmptyListener
import com.tolo.app.gitandhub.ui.widget.error.ErrorListener
import kotlinx.android.synthetic.main.activity_browse.*
import org.koin.android.ext.android.inject
import org.koin.android.scope.ext.android.bindScope
import org.koin.android.scope.ext.android.getCurrentScope
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber


class FavoriteActivity : AppCompatActivity() {

    private val browseAdapter: FavouriteAdapter by inject()

    private val viewModel: FavouriteViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse)
        bindScope(getCurrentScope())

        setupBrowseRecycler()
        setupViewListeners()

        viewModel.getRepos().observe(this,
            Observer<FavouriteState> {
                if (it != null) this.handleDataState(it)
            })
        viewModel.fetchRepos()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.detail_menu, menu)
        return true
    }

    private fun setupBrowseRecycler() {
        recycler_browse.layoutManager = LinearLayoutManager(this)
        recycler_browse.adapter = browseAdapter
    }

    private fun handleDataState(browseState: FavouriteState) {
        when (browseState) {
            is FavouriteState.Loading -> setupScreenForLoadingState()
            is FavouriteState.Success -> setupScreenForSuccess(browseState.data)
            is FavouriteState.Error -> setupScreenForError(browseState.errorMessage)
        }
    }

    private fun setupScreenForLoadingState() {
        progress.visibility = View.VISIBLE
        recycler_browse.visibility = View.GONE
        view_empty.visibility = View.GONE
        view_error.visibility = View.GONE
    }

    private fun setupScreenForSuccess(data: List<GithubRepo>?) {
        view_error.visibility = View.GONE
        progress.visibility = View.GONE
        if (data != null && data.isNotEmpty()) {
            updateListView(data)
            recycler_browse.visibility = View.VISIBLE
        } else {
            view_empty.visibility = View.VISIBLE
        }
    }

    private fun updateListView(repos: List<GithubRepo>) {
        browseAdapter.repos = repos
        browseAdapter.notifyDataSetChanged()
        browseAdapter.itemRepoClick = {
            Timber.i(it.toString())
            val intent = DetailActivity.callingIntent(this@FavoriteActivity, it)
            startActivity(intent)
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
            viewModel.fetchRepos()
        }
    }

    private val errorListener = object : ErrorListener {
        override fun onTryAgainClicked() {
            viewModel.fetchRepos()
        }
    }

    companion object {

        fun callingIntent(context: Context): Intent {
            return Intent(context, FavoriteActivity::class.java)
        }
    }

}