package com.dicoding.submission2.model

import com.google.gson.annotations.SerializedName

data class ResponseUser(

	@field:SerializedName("total_count")
	val totalCount: Int,

	@field:SerializedName("incomplete_results")
	val incompleteResults: Boolean,

	@field:SerializedName("items")
	val items: List<GithubItem>
)

data class GithubItem(

	@field:SerializedName("repos_url")
	val reposUrl: String,

	@field:SerializedName("following_url")
	val followingUrl: String,

	@field:SerializedName("bio")
	val bio: String,

	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("company")
	val company: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("public_repos")
	val publicRepos: Int,

	@field:SerializedName("gravatar_id")
	val gravatarId: String,

	@field:SerializedName("organizations_url")
	val organizationsUrl: String,

	@field:SerializedName("followers_url")
	val followersUrl: String,

	@field:SerializedName("public_gists")
	val publicGists: Int,

	@field:SerializedName("url")
	val url: String,

	@field:SerializedName("followers")
	val followers: Int,

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("following")
	val following: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("location")
	val location: String,
)
