package com.aquatic.lucre.core

import android.content.Intent
import androidx.fragment.app.Fragment

/**
 * Genereic BaseList Fragment class. This class
 * contains all the shared methods of the
 * various List Fragments to avoid code duplication
 */
abstract class BaseListFragment<T : BaseModel> : Fragment() {

    /* list for recycler view */
    abstract var list: MutableList<T>

    /* adapter for the recycler view */
    abstract var adapter: BaseAdapter<T>

    /* view model for custom model logic */
    abstract val model: BaseViewModel<T>

    /**
     * When a result comes in, we need to notify the
     * adapter that the underlying  data has changed
     * so we can refresh the view
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        adapter.notifyDataSetChanged()
        super.onActivityResult(requestCode, resultCode, data)
    }

    /**
     * Observe changes to the view model
     * store
     */
    abstract fun observeStore()
}
