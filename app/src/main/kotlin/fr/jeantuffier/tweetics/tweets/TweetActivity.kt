package fr.jeantuffier.tweetics.tweets

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import fr.jeantuffier.tweetics.R
import fr.jeantuffier.tweetics.common.Tweetics
import fr.jeantuffier.tweetics.common.model.tweet.Tweet
import kotlinx.android.synthetic.main.tweets_activity.*
import javax.inject.Inject

class TweetActivity : AppCompatActivity() {

    companion object {
        const val TITLE = "title"
        const val SCREEN_NAME = "screen_name"
    }

    @Inject
    lateinit var toolbarViewModel: ToolbarViewModel

    @Inject
    lateinit var tweetListViewModel: TweetListViewModel

    @Inject
    lateinit var adapter: TweetAdapter

    private val screenName by lazy { intent.extras.getString(SCREEN_NAME) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tweets_activity)

        injectDependency()
        loadContent()

        setToolbar()
        setRecyclerView()
    }

    private fun injectDependency() {
        Tweetics
            .applicationComponent
            .inject(this)
    }

    private fun loadContent() {
        tweetListViewModel
            .loadContent(screenName)
            .subscribe(
                { updateAdapter(it) },
                { showErrorMessage(it) }
            )
    }

    private fun updateAdapter(tweets: List<Tweet>) {
        adapter.tweets = tweets
        adapter.notifyDataSetChanged()
    }

    private fun setToolbar() {
        name.text = intent.extras.getString(TITLE)
        toolbarViewModel.setBackgroundImage(background, screenName)
        toolbarViewModel.setProfileImage(profile, screenName)
    }

    private fun setRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val dividerItemDecoration = DividerItemDecoration(this, RecyclerView.VERTICAL)
        recyclerView.addItemDecoration(dividerItemDecoration)
    }

    private fun showErrorMessage(throwable: Throwable) {
        val errorMessage = tweetListViewModel.getErrorMessage(throwable)
        Snackbar
            .make(container, errorMessage, Snackbar.LENGTH_SHORT)
            .show()
    }

}
