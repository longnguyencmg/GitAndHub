package com.tolo.app.cache.db.constants


object GithubConstants {

    const val TABLE_NAME = "repository"

    const val QUERY_REPOS = "SELECT * FROM $TABLE_NAME"

    const val DELETE_ALL_REPOS = "DELETE FROM $TABLE_NAME"

    const val QUERY_FAVOURITES = "SELECT * FROM $TABLE_NAME WHERE isFavourite =:liked"

}