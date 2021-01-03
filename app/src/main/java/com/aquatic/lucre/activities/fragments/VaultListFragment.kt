package com.aquatic.lucre.activities.fragments

import android.os.Bundle
import android.view.* // ktlint-disable no-wildcard-imports
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.aquatic.lucre.R
import com.aquatic.lucre.activities.VaultActivity
import com.aquatic.lucre.adapters.AdapterListener
import com.aquatic.lucre.adapters.BaseAdapter
import com.aquatic.lucre.adapters.VaultAdapter
import com.aquatic.lucre.models.Vault
import com.aquatic.lucre.viewmodels.VaultViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_vault_list.*
import kotlinx.android.synthetic.main.fragment_vault_list.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.intentFor

class VaultListFragment : BaseListFragment<Vault>(), AdapterListener<Vault>, AnkoLogger {

    override var list: MutableList<Vault> = ArrayList<Vault>()
    override var adapter = VaultAdapter(list, this) as BaseAdapter<Vault>
    override val model: VaultViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_vault_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.vaultRecyclerView.layoutManager = LinearLayoutManager(context)
        view.vaultRecyclerView.adapter = adapter
        floatingActionButton.setOnClickListener { switchActivity() }
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

    fun switchActivity() {
        startActivityForResult(
            context?.intentFor<VaultActivity>(),
            0
        )
    }

    override fun onItemClick(item: Vault) {
        startActivityForResult(
            context?.intentFor<VaultActivity>()?.putExtra("vault_edit", item),
            0
        )
    }
}
