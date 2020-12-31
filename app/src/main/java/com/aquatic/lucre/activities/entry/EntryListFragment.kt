package com.aquatic.lucre.activities.entry

import android.content.Intent
import android.os.Bundle
import android.view.* // ktlint-disable no-wildcard-imports
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.aquatic.lucre.R
import com.aquatic.lucre.adapters.AdapterListener
import com.aquatic.lucre.adapters.EntryAdapter
import com.aquatic.lucre.main.App
import com.aquatic.lucre.models.Entry
import kotlinx.android.synthetic.main.fragment_entry_list.*
import kotlinx.android.synthetic.main.fragment_entry_list.view.*
import org.jetbrains.anko.intentFor

class EntryListFragment : Fragment(), AdapterListener<Entry> {

    lateinit var app: App

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_entry_list, container, false)
        setHasOptionsMenu(true)

        app = context?.applicationContext as App

        view.entryRecyclerView.layoutManager = LinearLayoutManager(context)
        view.entryRecyclerView.adapter = EntryAdapter(app.entryStore.all(), this)

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        entryRecyclerView.adapter = EntryAdapter(app.entryStore.all(), this)
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onCardClick(entry: Entry) {
        startActivityForResult(
            context?.intentFor<EntryActivity>()?.putExtra("entry_edit", entry),
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
