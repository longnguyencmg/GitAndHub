package com.tolo.app.cache.db.constants


object OwnerConstants {

    const val TABLE_NAME = "owner"

    const val QUERY_OWNER = "SELECT * FROM $TABLE_NAME WHERE repo_id =:repo_id"

    const val DELETE_ALL_OWNER = "DELETE FROM $TABLE_NAME"

}