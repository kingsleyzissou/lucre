package com.aquatic.lucre.activities.entry

import android.os.Bundle
import android.view.* // ktlint-disable no-wildcard-imports
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.aquatic.lucre.R
import com.aquatic.lucre.activities.vault.VaultCardFragment
import com.aquatic.lucre.adapters.EntryAdapter
import com.aquatic.lucre.core.AdapterListener
import com.aquatic.lucre.core.BaseAdapter
import com.aquatic.lucre.core.BaseListFragment
import com.aquatic.lucre.models.Entry
import com.aquatic.lucre.viewmodels.EntryViewModel
import com.aquatic.lucre.viewmodels.VaultViewModel
import kotlinx.android.synthetic.main.fragment_category_list.*
import kotlinx.android.synthetic.main.fragment_entry_list.view.*
import org.jetbrains.anko.intentFor

class EntryListFragment : BaseListFragment<Entry>(), AdapterListener<Entry> {

    override var list: MutableList<Entry> = ArrayList()
    override var adapter = EntryAdapter(list, this) as BaseAdapter<Entry>
    override val model: EntryViewModel by activityViewModels()
    val vaultModel: VaultViewModel by activityViewModels()
    lateinit var child: VaultCardFragment
    /**
     * Inflate the view
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_entry_list, container, false)

    /**
     * Setup the view
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        child = VaultCardFragment.create(false)
        val tx = childFragmentManager.beginTransaction()
        tx.replace(R.id.childFragmentContainer, child).commit()
        view.entryRecyclerView.layoutManager = LinearLayoutManager(context)
        view.entryRecyclerView.adapter = adapter
        floatingActionButton.setOnClickListener { switchActivity() }
        observeStore()
    }

    /**
     * Observe the category list live data
     * and update the recycler view if there
     * are any changes
     */
    override fun observeStore() {
        model.list.observe(
            viewLifecycleOwner,
            Observer {
                adapter.list.clear()
                adapter.list.addAll(it)
                adapter.notifyDataSetChanged()
            }
        )
        vaultModel.list.observe(
            viewLifecycleOwner,
            Observer {
                floatingActionButton.visibility = if (it.isEmpty()) View.GONE else View.VISIBLE
            }
        )
    }

    /**
     * Go to the create category activity
     */
    private fun switchActivity() {
        startActivityForResult(
            context?.intentFor<EntryActivity>()?.putExtra("vault", child.vault?.id),
            0
        )
    }

    /**
     * Event listener for when a enty item is pressed.
     * This click event takes the user to the entry
     * edit activity
     */
    override fun onItemClick(item: Entry) {
        startActivityForResult(
            context?.intentFor<EntryActivity>()?.putExtra("entry_edit", item),
            0
        )
    }
}
