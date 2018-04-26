package fr.jeantuffier.tweetics.presentation.politician

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import dagger.android.AndroidInjection
import fr.jeantuffier.tweetics.R
import fr.jeantuffier.tweetics.domain.model.Politician
import kotlinx.android.synthetic.main.politicians_activity.*
import javax.inject.Inject

class PoliticianActivity : AppCompatActivity(), PoliticianContract.View {

    @Inject
    lateinit var politicianPresenter: PoliticianContract.Presenter

    private val adapter by lazy { PoliticianAdapter(emptyList()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.politicians_activity)

        title = getString(R.string.ministers)

        setToolbar()
        setRecyclerView()
        loadContent()

        PoliticianOnItemClickHandler(this)
            .registerOnClickHandler()
    }

    override fun updateViewState(state: PoliticianViewState) {
        when (state) {
            is PoliticianViewState.Loading -> showLoading()
            is PoliticianViewState.Loaded -> updateAdapter(state.politicians)
            is PoliticianViewState.Error -> showErrorMessage(state.error)
        }
    }

    private fun setToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.app_name)
    }

    private fun setRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun loadContent() {
        politicianPresenter.loadContent()
        showLoading()
    }

    private fun showLoading() {

    }

    private fun updateAdapter(politicians: List<Politician>) {
        adapter.politicians = politicians
        adapter.notifyDataSetChanged()
    }

    private fun showErrorMessage(error: String?) {
        error?.let {
            Snackbar
                .make(container, it, Snackbar.LENGTH_SHORT)
                .show()
        }
    }

}
