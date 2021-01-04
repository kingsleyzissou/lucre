package com.aquatic.lucre.utilities

/**
 * This class is used for managing exceptions in remote calls with in a view model.
 * It is quite tricky to manage events since all the firestore operations are run
 * with coroutines on separate threads and the method calls are all asyncronous.
 * To get around this, we can wrap the event in this DataState class and observe the
 * data state. We can then update the data state with the outcome of the remote
 * request.
 *
 * A reference for this can be found at the following link:
 * https://github.com/mitchtabian/Clean-Notes/blob/master/app/src/main/java/com/codingwithmitch/cleannotes/business/domain/state/DataState.kt
 */
data class DataState<T>(
    var loading: Boolean = false,
    var e: Exception? = null,
    var data: T? = null,
    var message: String? = null
) {

    /**
     * A companion object is used to simplify the creation
     * of the datastate for the different events.
     */
    companion object {

        /**
         * This is called when the data is
         * currently loading
         */
        fun <T> loading(
            loading: Boolean = true
        ): DataState<T> = DataState(loading)

        /**
         * This method is invoked when the app
         * has encountered an error
         */
        fun <T> error(
            e: Exception? = null,
            message: String? = null
        ): DataState<T> = DataState(false, e, null, message)

        /**
         * The data method is called once we have a result and the
         * data has been loaded.
         */
        fun <T> data(
            data: T? = null,
            message: String? = null
        ): DataState<T> = DataState(false, null, data, message)
    }
}
