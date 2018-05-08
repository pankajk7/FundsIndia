package fundsindia.pankaj.fundsindia.rest

import fundsindia.pankaj.fundsindia.BuildConfig
import fundsindia.pankaj.fundsindia.model.News
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NewsApi {

    @Headers("X-Mashape-Key: ${BuildConfig.API_KEY}")
    @GET("Search/NewsSearchAPI")
    fun fetchNewsData(@Query("q") query: String,
                      @Query("autocorrect") autoCorrect: Boolean = true,
                      @Query("count") count: Int = 20) : Single<Response<News>>
}