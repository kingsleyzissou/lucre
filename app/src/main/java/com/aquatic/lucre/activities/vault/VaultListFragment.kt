package com.aquatic.lucre.activities.vault

import android.content.Intent
import android.os.Bundle
import android.view.* // ktlint-disable no-wildcard-imports
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.aquatic.lucre.R
import com.aquatic.lucre.adapters.AdapterListener
import com.aquatic.lucre.adapters.VaultAdapter
import com.aquatic.lucre.main.App
import com.aquatic.lucre.models.Vault
import kotlinx.android.synthetic.main.fragment_vault_list.*
import kotlinx.android.synthetic.main.fragment_vault_list.view.*
import org.jetbrains.anko.intentFor

class VaultListFragment : Fragment(), AdapterListener<Vault> {

    lateinit var app: App

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_vault_list, container, false)
        setHasOptionsMenu(true)

        app = context?.applicationContext as App

        view.vaultRecyclerView.layoutManager = LinearLayoutManager(context)
        view.vaultRecyclerView.adapter = VaultAdapter(app.vaultStore.all(), this)

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        vaultRecyclerView.adapter = VaultAdapter(app.vaultStore.all(), this)
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onCardClick(vault: Vault) {
        startActivityForResult(
            context?.intentFor<VaultActivity>()?.putExtra("vault_edit", vault),
            0
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_vault_list, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.vault_add -> startActivityForResult(
                context?.intentFor<VaultActivity>(),
                0
            )
        }
        return super.onOptionsItemSelected(item)
    }
}
