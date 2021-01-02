package com.aquatic.lucre.activities.fragments

import android.content.Intent
import android.os.Bundle
import android.view.* // ktlint-disable no-wildcard-imports
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.aquatic.lucre.R
import com.aquatic.lucre.activities.EntryActivity
import com.aquatic.lucre.adapters.AdapterListener
import com.aquatic.lucre.adapters.BaseAdapter
import com.aquatic.lucre.adapters.EntryAdapter
import com.aquatic.lucre.models.Entry
import com.aquatic.lucre.viewmodels.EntryViewModel
import kotlinx.android.synthetic.main.fragment_entry_list.*
import kotlinx.android.synthetic.main.fragment_entry_list.view.*
import kotlinx.android.synthetic.main.fragment_vault_list.view.*
import org.jetbrains.anko.intentFor

class EntryListFragment : BaseListFragment<Entry>(), AdapterListener<Entry> {

    override var list: MutableList<Entry> = ArrayList()
    override var adapter = EntryAdapter(list, this) as BaseAdapter<Entry>
    override val model: EntryViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_entry_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        view.entryRecyclerView.layoutManager = LinearLayoutManager(context)
        view.entryRecyclerView.adapter = adapter
        observeStore()
    }

    override fun observeStore() {
        model.list.observe(
            viewLifecycleOwner,
            Observer {
                adapter.list.clear()
                adapter.list.addAll(it)
                adapter.notifyDataSetChanged()
            }
        )
    }

    override fun onCardClick(item: Entry) {
        startActivityForResult(
            context?.intentFor<EntryActivity>()?.putExtra("entry_edit", item),
            0
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_entry_list, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.entry_add -> startActivityForResult(
                context?.intentFor<EntryActivity>(),
                0
            )
        }
        return super.onOptionsItemSelected(item)
    }
}
