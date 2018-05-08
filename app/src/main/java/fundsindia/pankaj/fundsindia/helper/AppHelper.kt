package fundsindia.pankaj.fundsindia.helper

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.text.Html
import android.text.TextUtils

class AppHelper {
    companion object {
        private val LT_ESCAPE_STRING = "&lt;"
        private val GT_ESCAPE_STRING = "&gt;"

        fun isFinished(context: Context?): Boolean {
            if (context == null) return true
            if (context is Activity) {
                val activity = context as Activity?
                return activity?.isDestroyed == true || activity?.isFinishing == true
            }
            return false
        }

        fun isNetworkAvailable(ctx: Context): Boolean {
            val connectivityManager = ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }

        fun fromHtml(value: String?): CharSequence? {
            if (TextUtils.isEmpty(value))
                return null
            val charSequence = wrappedFromHtml(value!!)
            if (TextUtils.isEmpty(charSequence))
                return null
            return if (!(value.contains(LT_ESCAPE_STRING) && value.contains(GT_ESCAPE_STRING))) {
                charSequence
            } else wrappedFromHtml(charSequence.toString())
        }

        @TargetApi(Build.VERSION_CODES.N)
        private fun wrappedFromHtml(value: String): CharSequence {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                Html.fromHtml(value, Html.FROM_HTML_MODE_LEGACY)
            else
                Html.fromHtml(value)
        }
    }
}