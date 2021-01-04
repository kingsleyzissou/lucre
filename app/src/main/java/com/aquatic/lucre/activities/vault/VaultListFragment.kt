package com.aquatic.lucre.activities.vault

import android.os.Bundle
import android.view.* // ktlint-disable no-wildcard-imports
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.aquatic.lucre.R
import com.aquatic.lucre.adapters.VaultAdapter
import com.aquatic.lucre.core.AdapterListener
import com.aquatic.lucre.core.BaseAdapter
import com.aquatic.lucre.core.BaseListFragment
import com.aquatic.lucre.models.Vault
import com.aquatic.lucre.viewmodels.EntryViewModel
import com.aquatic.lucre.viewmodels.VaultViewModel
import kotlinx.android.synthetic.main.fragment_vault_list.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor

class VaultListFragment : BaseListFragment<Vault>(), AdapterListener<Vault>, AnkoLogger {

    override var list: MutableList<Vault> = ArrayList<Vault>()
    override var adapter = VaultAdapter(list, this) as BaseAdapter<Vault>
    override val model: VaultViewModel by activityViewModels()
    val entryModel: EntryViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_vault_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val child = VaultCardFragment()
        val tx = childFragmentManager.beginTransaction()
        tx.replace(R.id.childFragmentContainer, child).commit()
        view.vaultRecyclerView.layoutManager = LinearLayoutManager(context)
        view.vaultRecyclerView.adapter = adapter
//        floatingActionButton.setOnClickListener { switchActivity() }
        observeStore()
    }

    override fun observeStore() {
        entryModel.list.observe(
            viewLifecycleOwner,
            Observer {
                info("There was a change, $it")
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
