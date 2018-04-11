package fr.jeantuffier.tweetics.tweets

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
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
    lateinit var collapsingToolbarViewModel: CollapsingToolbarViewModel

    @Inject
    lateinit var tweetListViewModel: TweetListViewModel

    private val adapter by lazy { TweetAdapter(emptyList()) }

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
        setSupportActionBar(toolbar)
        supportActionBar?.title = intent.extras.getString(TITLE)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        collapsingToolbarViewModel.setImage(collapsingToolbarImage, screenName)
    }

    private fun setRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun showErrorMessage(throwable: Throwable) {
        val errorMessage = tweetListViewModel.getErrorMessage(throwable)
        Snackbar
            .make(container, errorMessage, Snackbar.LENGTH_SHORT)
            .show()
    }
}
