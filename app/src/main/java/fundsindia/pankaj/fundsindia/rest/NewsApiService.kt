package fundsindia.pankaj.fundsindia.rest

import fundsindia.pankaj.fundsindia.model.News
import io.reactivex.Single
import retrofit2.Response

object NewsApiService {

    private val newsApiService by lazy {
        ApiClient.client.create(NewsApi::class.java)
    }

    fun fetchNews(query: String): Single<Response<News>> {
        return newsApiService.fetchNewsData(query)
    }
}