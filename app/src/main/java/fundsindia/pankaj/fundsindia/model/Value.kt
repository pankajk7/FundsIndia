package fundsindia.pankaj.fundsindia.model

import com.google.gson.annotations.SerializedName

class Value {
    @SerializedName("title")
    val title: String? = null
    @SerializedName("description")
    val description: String? = null
    @SerializedName("datePublished")
    val datePublished: String? = null
    @SerializedName("image")
    val image: Image? = null
    @SerializedName("provider")
    val provider: Provider? = null
    @SerializedName("url")
    val url: String? = null
}