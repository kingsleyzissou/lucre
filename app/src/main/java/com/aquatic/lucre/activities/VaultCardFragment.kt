package com.aquatic.lucre.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.aquatic.lucre.R
import com.aquatic.lucre.adapters.AdapterListener
import com.aquatic.lucre.models.Entry
import com.aquatic.lucre.models.Vault
import com.aquatic.lucre.viewmodels.EntryViewModel
import com.aquatic.lucre.viewmodels.VaultViewModel
import kotlinx.android.synthetic.main.fragment_vault_card.*
import org.jetbrains.anko.AnkoLogger


class VaultCardFragment : Fragment(), AdapterListener<Vault>, AnkoLogger {

    var vault: Vault = Vault()
    var balance: Float = 0F
    var vaults: MutableList<Vault> = ArrayList()
    var entries: MutableList<Entry> = ArrayList()

    private lateinit var bottomSheet: VaultListDialogFragment

    private val vaultModel: VaultViewModel by activityViewModels()
    private val entryModel: EntryViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_vault_card, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dashboardBalance.setOnClickListener { showModal() }
        observeModels()
    }

    private fun observeModels() {
        vaultModel.list.observe(
            viewLifecycleOwner,
            Observer {
                vaults.clear()
                vaults.addAll(it)
                updateVault(it[0])
            }
        )
        entryModel.list.observe(
            viewLifecycleOwner,
            Observer {
                entries.clear()
                entries.addAll(it)
            }
        )
        entryModel.balance.observe(
            viewLifecycleOwner,
            Observer {
                balance = it
                dashboardBalance.setText("${vault.currency} $balance")
            }
        )
    }

    private fun showModal() {
        bottomSheet = VaultListDialogFragment.create(vaults)
        bottomSheet.listener = this
        bottomSheet.show(childFragmentManager, "dialog")
    }

    override fun onItemClick(item: Vault) {
        updateVault(item)
        bottomSheet.dismiss()
    }

    private fun updateVault(vault: Vault) {
        this.vault = vault
        dashboardVault.setText(vault.name)
        entryModel.getEntries(vault.id)
    }
}
