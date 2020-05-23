package com.tolo.app.gitandhub.ui.browse

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.tolo.app.data.model.GithubRepo
import com.tolo.app.gitandhub.R
import com.tolo.app.gitandhub.ui.detail.DetailActivity
import com.tolo.app.gitandhub.ui.favourite.FavoriteActivity
import com.tolo.app.gitandhub.ui.widget.empty.EmptyListener
import com.tolo.app.gitandhub.ui.widget.error.ErrorListener
import kotlinx.android.synthetic.main.activity_browse.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class BrowseActivity : AppCompatActivity() {

    private val browseAdapter: BrowseAdapter by inject()

    private val viewModel: BrowseGithubRepoViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse)

        setupBrowseRecycler()
        setupViewListeners()

        viewModel.getRepos().observe(this,
            Observer<BrowseState> {
                if (it != null) this.handleDataState(it)
            })
        viewModel.fetchRepos()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.detail_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.view_favourites -> {
                val intent = FavoriteActivity.callingIntent(this@BrowseActivity)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupBrowseRecycler() {
        recycler_browse.layoutManager = LinearLayoutManager(this)
        recycler_browse.adapter = browseAdapter
    }

    private fun handleDataState(browseState: BrowseState) {
        when (browseState) {
            is BrowseState.Loading -> setupScreenForLoadingState()
            is BrowseState.Success -> setupScreenForSuccess(browseState.data)
            is BrowseState.Error -> setupScreenForError(browseState.errorMessage)
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
            val intent = DetailActivity.callingIntent(this@BrowseActivity, it)
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

}