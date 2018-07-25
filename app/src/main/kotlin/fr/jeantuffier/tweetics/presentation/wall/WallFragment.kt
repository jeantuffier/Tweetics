package fr.jeantuffier.tweetics.presentation.wall

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fr.jeantuffier.tweetics.R
import fr.jeantuffier.tweetics.domain.model.Tweet
import kotlinx.android.synthetic.main.wall_fragment.*
import org.koin.android.ext.android.inject

class WallFragment : Fragment(), WallContract.View {

    private val presenter: WallContract.Presenter by inject()

    private val adapter: WallAdapter by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.wall_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.attach(this)
        setRecyclerView()
        presenter.loadContent()
    }

    override fun updateViewState(state: WallViewState) {
        when (state) {
            is WallViewState.Loading -> showLoading()
            is WallViewState.Loaded -> updateAdapter(state.tweets)
            is WallViewState.Error -> showErrorMessage(state.error)
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.attach(this)
    }

    override fun onPause() {
        super.onPause()
        presenter.detach()
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

    private fun setRecyclerView() {
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager

        val dividerItemDecoration = DividerItemDecoration(recyclerView.context,
            layoutManager.orientation)
        recyclerView.addItemDecoration(dividerItemDecoration)

        recyclerView.adapter = adapter
    }

}
