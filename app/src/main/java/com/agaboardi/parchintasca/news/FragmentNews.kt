package com.agaboardi.parchintasca.news


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import com.agaboardi.parchintasca.R
import com.agaboardi.parchintasca.common.injection.Injection
import com.agaboardi.parchintasca.news.domain.model.News
import com.agaboardi.parchintasca.news.domain.ui.NewsDivider
import com.agaboardi.parchintasca.newsdetail.NewsDetailActivity
import io.reactivex.internal.schedulers.NewThreadWorker
import kotlinx.android.synthetic.main.fragment_news.*
import org.jetbrains.anko.support.v4.dip

class FragmentNews : Fragment(), NewsContract.View {
    private var presenter: NewsContract.Presenter? = null
    private var adapter: NewsAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupAdapter()

        NewsPresenter(this, Injection.provideUseCaseHandler(), Injection.provideGetNews())

        setupUiInteraction()
    }

    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
        presenter?.start()
    }

    override fun setPresenter(presenter: NewsContract.Presenter) {
        this.presenter = presenter
    }

    private fun setupAdapter() {
        val dividerDrawable = ContextCompat.getDrawable(activity!!, R.drawable.news_divider)

        adapter = NewsAdapter {
            presenter?.openNews(it)
        }
        newsRecycler.layoutManager = LinearLayoutManager(activity)
        newsRecycler.adapter = adapter
        dividerDrawable?.let {
            newsRecycler.addItemDecoration(NewsDivider(it, dip(16), dip(32)))
        }
    }

    private fun setupUiInteraction() {
        swipeRefresh.setOnRefreshListener { presenter?.loadNews(true) }
    }

    override fun showNews(news: List<News>) {
        adapter?.replaceNews(news)
    }

    override fun goToNewsDetail(news: News) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(news.link)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }

    override fun setLoadingNews(visible: Boolean) {
        swipeRefresh.isRefreshing = visible
    }

    override fun showNewsError() {

    }

    override fun isValid(): Boolean = !isDetached && !isRemoving

    companion object {
        fun newInstance() = FragmentNews()
    }
}
