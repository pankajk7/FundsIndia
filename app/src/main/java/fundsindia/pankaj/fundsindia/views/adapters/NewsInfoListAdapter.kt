package fundsindia.pankaj.fundsindia.views.adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import fundsindia.pankaj.fundsindia.R
import fundsindia.pankaj.fundsindia.helper.AppHelper
import fundsindia.pankaj.fundsindia.model.Value
import fundsindia.pankaj.fundsindia.views.WebViewActivity
import kotlinx.android.synthetic.main.view_new_listing_item_layout.view.*

class NewsInfoListAdapter(private val context: Context,
                          private var dataList: List<Value>?) :
        RecyclerView.Adapter<NewsInfoListAdapter.ListingViewHolder>() {

    private var layoutInflater: LayoutInflater = LayoutInflater.from(context)

    fun setList(dataList: List<Value>?) {
        this.dataList = dataList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListingViewHolder {
        return ListingViewHolder(layoutInflater.inflate(R.layout.view_new_listing_item_layout,
                parent, false))
    }

    override fun getItemCount(): Int {
        return dataList?.size ?: 0
    }

    override fun onBindViewHolder(holder: ListingViewHolder, position: Int) {
        val objValue = dataList?.get(position) ?: return
        setImage(objValue.image?.url, holder.itemView.iv_url_image)
        holder.itemView.tv_title.text = AppHelper.fromHtml(objValue.title)
        holder.itemView.tv_info.text = AppHelper.fromHtml(objValue.description)
        holder.itemView.tag = position
        holder.itemView.setOnClickListener(onClickListener)
    }

    private val onClickListener = View.OnClickListener { v ->
        val position = v.tag as Int
        val objValue = dataList?.get(position) ?: return@OnClickListener
        val intent = Intent(context, WebViewActivity::class.java)
        intent.putExtra(WebViewActivity.URL_ADDRESS, objValue.url)
        context.startActivity(intent)
    }

    private fun setImage(imageUrl: String?, imageView: ImageView) {
        val requestOptions = RequestOptions()
        requestOptions.placeholder(R.drawable.no_image)
        requestOptions.error(R.drawable.no_image)
        Glide.with(context)
                .load(imageUrl)
                .apply(requestOptions)
                .into(imageView)
    }

    class ListingViewHolder constructor(itemView: View?) : RecyclerView.ViewHolder(itemView)
}