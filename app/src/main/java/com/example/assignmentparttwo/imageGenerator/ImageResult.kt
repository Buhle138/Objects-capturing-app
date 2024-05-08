package com.example.assignmentparttwo.imageGenerator

import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class ProfileImage(
    val small: String,
    val medium: String,
    val large: String,
)
@Serializable
data class UnsplashImage(
    val id: String,
    val urlsval : List<Urls>,
    val likes: Int,
    val user: User
)
@Serializable
data class ApiResponse(
    val results: List<Result>
)


@Serializable
data class Result(
    val id: String,
    val slug: String,
    @JsonProperty("alternative_slugs")
    val createdAt: String,
    @JsonProperty("updated_at")
    val updatedAt: String,
    @JsonProperty("promoted_at")
    val promotedAt: String?,
    val width: Long,
    val height: Long,
    val color: String,
    @JsonProperty("blur_hash")
    val blurHash: String,
    val description: String?,
    @JsonProperty("alt_description")
    val altDescription: String,
    val urls: Urls,
    val likes: Long,
    @JsonProperty("liked_by_user")
    val likedByUser: Boolean,
    @JsonProperty("current_user_collections")
    val currentUserCollections: List<Any?>,
    val sponsorship: Any?,
    @JsonProperty("asset_type")
    val assetType: String,
)

@Serializable
data class Urls(
    val raw: String,
    val full: String,
    val regular: String,
    val small: String,
    val thumb: String,
    val smallS3: String,
)

data class SearchModel(
    val urls: List<Urls>
)

data class User(
    @SerialName("links")
    val username: String
)


data class Links2(
    val self: String,
    val html: String,
    val photos: String,
    val likes: String,
    val portfolio: String,
    val following: String,
    val followers: String,
)