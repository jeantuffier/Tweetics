package fr.jeantuffier.tweetics.presentation.politician

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fr.jeantuffier.tweetics.R
import fr.jeantuffier.tweetics.domain.model.Politician
import kotlinx.android.synthetic.main.politicians_activity.*
import org.koin.android.ext.android.inject

class PoliticianFragment : Fragment(), PoliticianContract.View {

    private val politicianPresenter: PoliticianContract.Presenter by inject()

    private val adapter: PoliticianAdapter by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.politicians_activity, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //title = getString(R.string.ministers)

        setToolbar()
        setRecyclerView()
        loadContent()
    }

    override fun updateViewState(state: PoliticianViewState) {
        when (state) {
            is PoliticianViewState.Loading -> showLoading()
            is PoliticianViewState.Loaded -> updateAdapter(state.politicians)
            is PoliticianViewState.Error -> showErrorMessage(state.error)
        }
    }

    private fun setToolbar() {
        //setSupportActionBar(toolbar)
        //supportActionBar?.title = getString(R.string.app_name)
    }

    private fun setRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
    }

    private fun loadContent() {
        politicianPresenter.loadContent()
        showLoading()
    }

    private fun showLoading() {

    }

    private fun updateAdapter(politicians: List<Politician>) {
        adapter.politicians.clear()
        adapter.politicians.addAll(politicians)
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
