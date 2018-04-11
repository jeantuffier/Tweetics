package fr.jeantuffier.tweetics.politician

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import fr.jeantuffier.tweetics.R
import fr.jeantuffier.tweetics.common.Tweetics
import fr.jeantuffier.tweetics.common.model.politician.Politician
import kotlinx.android.synthetic.main.politicians_activity.*
import javax.inject.Inject

class PoliticianActivity : AppCompatActivity() {

    @Inject
    lateinit var politicianListViewModel: PoliticianListViewModel

    private val adapter by lazy { PoliticianAdapter(emptyList()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.politicians_activity)

        title = getString(R.string.ministers)

        injectDependency()
        loadContent()

        setToolbar()
        setRecyclerView()

        PoliticianOnItemClickHandler(this)
            .registerOnClickHandler()
    }

    private fun injectDependency() {
        Tweetics
            .applicationComponent
            .inject(this)
    }

    private fun loadContent() {
        politicianListViewModel
            .loadContent()
            .subscribe(
                { updateAdapter(it) },
                { showErrorMessage(it) }
            )
    }

    private fun setToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.app_name)
    }

    private fun setRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun updateAdapter(politicians: List<Politician>) {
        adapter.politicians = politicians
        adapter.notifyDataSetChanged()
    }

    private fun showErrorMessage(throwable: Throwable) {
        val errorMessage = politicianListViewModel.getErrorMessage(throwable)
        Snackbar
            .make(container, errorMessage, Snackbar.LENGTH_SHORT)
            .show()
    }

}
