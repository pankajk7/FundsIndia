package fundsindia.pankaj.fundsindia.model

import com.google.gson.annotations.SerializedName

class News {

    @SerializedName("_type")
    val type: String? = null
    @SerializedName("relatedSearch")
    val relatedSearch: List<String>? = null
    @SerializedName("totalCount")
    val totalCount: String? = null
    @SerializedName("value")
    val value: List<Value>? = null
    @SerializedName("didUMean")
    val didUMean: String? = null
}