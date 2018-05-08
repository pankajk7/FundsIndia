package fundsindia.pankaj.fundsindia.helper

import android.widget.TextView
import com.jakewharton.rxbinding2.widget.RxTextView
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

class RXBounceSearchHelper {

    private val DEFAULT_SEARCH_DEBOUNCE_TIME_IN_MS = 250

    private var searchBarSubscription: Disposable? = null

    fun subscribe(textView: TextView, textChangeObserver: ObserverAdapter<TextViewTextChangeEvent>) {
        this.subscribe(textView, textChangeObserver, DEFAULT_SEARCH_DEBOUNCE_TIME_IN_MS.toLong())
    }

    fun subscribe(textView: TextView, textChangeObserver: ObserverAdapter<TextViewTextChangeEvent>,
                  deBounceInMs: Long) {
        RxTextView.textChangeEvents(textView)
                .debounce(deBounceInMs, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : ObserverAdapter<TextViewTextChangeEvent>() {
                    override fun onNext(textViewTextChangeEvent: TextViewTextChangeEvent) {
                        textChangeObserver.onNext(textViewTextChangeEvent)
                    }

                    override fun onSubscribe(d: Disposable) {
                        super.onSubscribe(d)
                        textChangeObserver.onSubscribe(d)
                        searchBarSubscription = d
                    }

                    override fun onComplete() {
                        super.onComplete()
                        textChangeObserver.onComplete()
                    }

                    override fun onError(e: Throwable) {
                        super.onError(e)
                        textChangeObserver.onError(e)
                    }
                })
    }

    fun unSubscribe() {
        if (searchBarSubscription != null) {
            searchBarSubscription!!.dispose()
            searchBarSubscription = null
        }
    }
}