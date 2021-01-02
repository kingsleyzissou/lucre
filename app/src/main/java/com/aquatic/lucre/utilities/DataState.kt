package com.aquatic.lucre.utilities

/**
 * Reference:
 * https://github.com/mitchtabian/Clean-Notes/blob/master/app/src/main/java/com/codingwithmitch/cleannotes/business/domain/state/DataState.kt
 */
data class DataState<T>(
    var loading: Boolean = false,
    var e: Exception? = null,
    var data: T? = null,
    var message: String? = null
) {
    companion object {

        fun <T> loading(
            loading: Boolean = true
        ) : DataState<T> = DataState(loading)

        fun <T> error (
            e: Exception? = null,
            message: String? = null
        ): DataState<T> = DataState(false, e, null, message)

        fun <T> data (
            data: T? = null,
            message: String? = null
        ): DataState<T> = DataState(false,null, data, message)

    }
}