package com.aquatic.lucre.activities.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aquatic.lucre.adapters.BaseAdapter
import com.aquatic.lucre.models.Model
import com.aquatic.lucre.viewmodels.BaseViewModel
import kotlinx.android.synthetic.main.fragment_vault_list.view.*

abstract class BaseListFragment<T : Model> : Fragment() {
    abstract var list: MutableList<T>
    abstract var adapter: BaseAdapter<T>
    abstract val model: BaseViewModel<T>

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        adapter.notifyDataSetChanged()
        super.onActivityResult(requestCode, resultCode, data)
    }

    abstract fun observeStore()
}
