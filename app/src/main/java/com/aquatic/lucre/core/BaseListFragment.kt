package com.aquatic.lucre.core

import android.content.Intent
import androidx.fragment.app.Fragment

abstract class BaseListFragment<T : BaseModel> : Fragment() {
    abstract var list: MutableList<T>
    abstract var adapter: BaseAdapter<T>
    abstract val model: BaseViewModel<T>

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        adapter.notifyDataSetChanged()
        super.onActivityResult(requestCode, resultCode, data)
    }

    abstract fun observeStore()
}
