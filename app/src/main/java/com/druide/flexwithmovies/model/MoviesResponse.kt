package com.druide.flexwithmovies.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


@Parcelize
@Serializable
data class Movies(
    @SerialName("page") val page: Int,

    @SerialName("results") val results: List<Results>,

    @SerialName("total_pages") val totalPages: Int,

    @SerialName("total_results") val totalResults: Int

) : Parcelable {
    override fun toString(): String {
        return Json.encodeToString(this)
    }
}


@Parcelize
@Serializable
data class Results(
    @SerialName("adult") val adult: Boolean,

    @SerialName("backdrop_path") val backdropPath: String?,

    @SerialName("genre_ids") val genreIds: List<Int>,

    @SerialName("id") val id: Int,

    @SerialName("original_language") val originalLanguage: String,

    @SerialName("original_title") val originalTitle: String,

    @SerialName("overview") val overview: String,

    @SerialName("popularity") val popularity: Double,

    @SerialName("poster_path") val poster_path: String?,

    @SerialName("release_date") val release_date: String,

    @SerialName("title") val title: String,

    @SerialName("video") val video: Boolean,

    @SerialName("vote_average") val voteAverage: Double,

    @SerialName("vote_count") val voteCount: Int

) : Parcelable {
    override fun toString(): String {
        return Json.encodeToString(this)
    }
}
