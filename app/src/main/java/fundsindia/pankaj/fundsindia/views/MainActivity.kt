package fundsindia.pankaj.fundsindia.views

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent
import fundsindia.pankaj.fundsindia.R
import fundsindia.pankaj.fundsindia.helper.AppHelper
import fundsindia.pankaj.fundsindia.helper.ObserverAdapter
import fundsindia.pankaj.fundsindia.helper.RXBounceSearchHelper
import fundsindia.pankaj.fundsindia.model.News
import fundsindia.pankaj.fundsindia.rest.NewsApiService
import fundsindia.pankaj.fundsindia.views.adapters.NewsInfoListAdapter
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private var searchResultSubscription: Disposable? = null
    private val rxBounceSearchHelper = RXBounceSearchHelper()
    private var newsInfoListAdapter: NewsInfoListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rv_news_info.layoutManager = LinearLayoutManager(this)
        newsInfoListAdapter = NewsInfoListAdapter(this@MainActivity, null)
        rv_news_info.adapter = newsInfoListAdapter
    }

    override fun onStart() {
        super.onStart()
        rxBounceSearchHelper.subscribe(et_search, getSearchBarObserver())
    }

    override fun onStop() {
        unSubscribeSearchResultSubscription()
        super.onStop()
    }

    override fun onDestroy() {
        rxBounceSearchHelper.unSubscribe()
        super.onDestroy()
    }

    private fun getSearchBarObserver(): ObserverAdapter<TextViewTextChangeEvent> {
        return object : ObserverAdapter<TextViewTextChangeEvent>() {

            override fun onNext(onTextChangeEvent: TextViewTextChangeEvent) {
                val query = onTextChangeEvent.text().toString()
                if (!TextUtils.isEmpty(query)) {
                    performSearch(query)
                }
            }
        }
    }

    private fun performSearch(query: String) {
        if (!AppHelper.isNetworkAvailable(this@MainActivity)) {
            Toast.makeText(this@MainActivity,
                    getString(R.string.no_internet), Toast.LENGTH_LONG).show()
            return
        }
        progressBar.visibility = View.VISIBLE
        unSubscribeSearchResultSubscription()
        NewsApiService.fetchNews(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<Response<News>> {
                    override fun onSuccess(response: Response<News>) {
                        if (AppHelper.isFinished(this@MainActivity)) return
                        progressBar.visibility = View.GONE
                        unSubscribeSearchResultSubscription()
                        if (response.isSuccessful) {
                            newsInfoListAdapter?.setList(response.body()?.value)
                        }
                    }

                    override fun onSubscribe(d: Disposable) {
                        searchResultSubscription = d
                    }

                    override fun onError(e: Throwable) {
                        if (AppHelper.isFinished(this@MainActivity)) return
                        progressBar.visibility = View.GONE
                        Toast.makeText(this@MainActivity,
                                getString(R.string.some_error_occurred), Toast.LENGTH_LONG).show()
                    }

                })
    }

    private fun unSubscribeSearchResultSubscription() {
        searchResultSubscription?.dispose()
        searchResultSubscription = null
    }
}
