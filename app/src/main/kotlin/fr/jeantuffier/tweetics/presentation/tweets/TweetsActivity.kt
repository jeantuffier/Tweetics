package fr.jeantuffier.tweetics.presentation.tweets

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.squareup.picasso.Picasso
import dagger.android.AndroidInjection
import fr.jeantuffier.tweetics.R
import fr.jeantuffier.tweetics.domain.model.Tweet
import fr.jeantuffier.tweetics.presentation.common.picasso.BlurImage
import fr.jeantuffier.tweetics.presentation.common.picasso.CircleImage
import kotlinx.android.synthetic.main.tweets_activity.*
import javax.inject.Inject

class TweetsActivity : AppCompatActivity(), TweetsContract.View {

    companion object {
        const val TITLE = "title"
        const val SCREEN_NAME = "screen_name"
    }

    @Inject
    lateinit var presenter: TweetsContract.Presenter

    @Inject
    lateinit var adapter: TweetsAdapter

    private val screenName by lazy { intent.extras.getString(SCREEN_NAME) }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tweets_activity)

        presenter.loadContent(screenName)

        setToolbar()
        setRecyclerView()
    }

    override fun updateViewState(state: TweetsViewState) {
        when (state) {
            is TweetsViewState.Loading -> showLoading()
            is TweetsViewState.Loaded -> updateAdapter(state.tweets)
            is TweetsViewState.Error -> showErrorMessage(state.error)
        }
    }

    private fun showLoading() {

    }

    private fun showErrorMessage(error: String?) {
        error?.let {
            Snackbar
                .make(container, error, Snackbar.LENGTH_SHORT)
                .show()
        }
    }

    private fun updateAdapter(tweets: List<Tweet>) {
        adapter.tweets = tweets
        adapter.notifyDataSetChanged()
    }

    private fun setToolbar() {
        name.text = intent.extras.getString(TITLE)
        val url = presenter.getImageUrl(screenName)
        setBackgroundImage(url)
        setProfileImage(url)
    }

    private fun setBackgroundImage(url: String) {
        Picasso.get()
            .load(url)
            .transform(BlurImage(background.context, 25f))
            .into(background)
    }

    private fun setProfileImage(url: String) {
        Picasso.get()
            .load(url)
            .transform(CircleImage())
            .into(profile)
    }

    private fun setRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val dividerItemDecoration = DividerItemDecoration(this, RecyclerView.VERTICAL)
        recyclerView.addItemDecoration(dividerItemDecoration)
    }

}