package fundsindia.pankaj.fundsindia.views

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import fundsindia.pankaj.fundsindia.R
import kotlinx.android.synthetic.main.activity_webview_layout.*


class WebViewActivity : AppCompatActivity() {

    companion object {
        val URL_ADDRESS = "url"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview_layout)

        val url = intent.getStringExtra(URL_ADDRESS)
        if (TextUtils.isEmpty(url)) {
            Toast.makeText(this@WebViewActivity,
                    getString(R.string.not_url_found), Toast.LENGTH_LONG).show()
            finish()
            return
        }
        webview.settings.javaScriptEnabled = true
        webview.webViewClient = WebViewClient()
        pb_webview.visibility = View.VISIBLE
        webview.loadUrl(url)
        webview.webViewClient = object: WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                pb_webview.visibility = View.GONE
            }

            override fun onPageCommitVisible(view: WebView?, url: String?) {
                super.onPageCommitVisible(view, url)
                pb_webview.visibility = View.GONE
            }
        }
    }
}