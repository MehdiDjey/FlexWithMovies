package com.druide.flexwithmovies.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


@Parcelize
@Serializable
data class Movie(

    @SerialName("adult") val adult: Boolean,

    @SerialName("backdrop_path") val backdropPath: String?,

    @SerialName("belongs_to_collection") val belongsToCollection: BelongsToCollection?,

    @SerialName("budget") val budget: Int,

    @SerialName("genres") val genres: List<Genres>,

    @SerialName("homepage") val homepage: String?,

    @SerialName("id") val id: Int,

    @SerialName("imdb_id") val imdbId: String?,

    @SerialName("original_language") val originalLanguage: String,

    @SerialName("original_title") val original_title: String,

    @SerialName("overview") val overview: String?,

    @SerialName("popularity") val popularity: Double,

    @SerialName("poster_path") val posterPath: String?,

    @SerialName("production_companies") val productionCompanies: List<ProductionCompanies>,

    @SerialName("production_countries") val productionCountries: List<ProductionCountries>,

    @SerialName("release_date") val releaseDate: String ="",

    @SerialName("revenue") val revenue: Int,

    @SerialName("runtime") val runtime: Int?,

    @SerialName("spoken_languages") val spokenLanguages: List<SpokenLanguages>,

    @SerialName("status") val status: String,

    @SerialName("tagline") val tagline: String?,

    @SerialName("title") val title: String,

    @SerialName("video") val video: Boolean,

    @SerialName("vote_average") val voteAverage: Double,

    @SerialName("vote_count") val voteCount: Int


) : Parcelable {
    override fun toString(): String {
        return Json.encodeToString(this)
    }
}

@Parcelize
@Serializable
data class Genres(

    @SerialName("id") val id: Int,

    @SerialName("name") val name: String

) : Parcelable {
    override fun toString(): String {
        return Json.encodeToString(this)
    }
}

@Parcelize
@Serializable
data class ProductionCompanies(

    @SerialName("id") val id: Int,

    @SerialName("logo_path") val logoPath: String?,

    @SerialName("name") val name: String,

    @SerialName("origin_country") val originCountry: String


) : Parcelable {
    override fun toString(): String {
        return Json.encodeToString(this)
    }
}

@Parcelize
@Serializable
data class ProductionCountries(

    @SerialName("iso_3166_1") val iso3166_1: String,

    @SerialName("name") val name: String

) : Parcelable {
    override fun toString(): String {
        return Json.encodeToString(this)
    }
}

@Parcelize
@Serializable
data class SpokenLanguages(

    @SerialName("english_name") val englishName: String,

    @SerialName("iso_639_1") val iso639_1: String,

    @SerialName("name") val name: String

) : Parcelable {
    override fun toString(): String {
        return Json.encodeToString(this)
    }
}


@Parcelize
@Serializable
data class BelongsToCollection (
    @SerialName("id") val id : Int,
    @SerialName("name") val name : String,
    @SerialName("poster_path") val poster_path : String,
    @SerialName("backdrop_path") val backdrop_path : String
) : Parcelable {
    override fun toString(): String {
        return Json.encodeToString(this)
    }
}