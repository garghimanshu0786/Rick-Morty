package model

import com.google.gson.annotations.SerializedName

class Episodes (
    val info : Info,
    @SerializedName("results")
    val episodes: List<Episode>
)

data class Episode (
    @SerializedName("id") val id : Int,
    @SerializedName("name") val name : String,
    @SerializedName("air_date") val air_date : String,
    @SerializedName("episode") val episode : String,
    @SerializedName("characters") val characters : List<String>,
    @SerializedName("url") val url : String,
    @SerializedName("created") val created : String
)

data class Info(val count : Int?, val pages: String?, val next: String?, val prev : String?)

