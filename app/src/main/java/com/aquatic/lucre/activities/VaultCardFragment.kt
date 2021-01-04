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

private const val ARG_DASHBOARD = "dashboard"

class VaultCardFragment : Fragment(), AdapterListener<Vault>, AnkoLogger {

    var dashboard: Boolean? = null
    var vault: Vault = Vault()
    var balance: Float = 0F
    var vaults: MutableList<Vault> = ArrayList()
    var entries: MutableList<Entry> = ArrayList()

    private lateinit var menuDialog: MenuDialogFragment
    private lateinit var listDialog: VaultListDialogFragment

    private val vaultModel: VaultViewModel by activityViewModels()
    private val entryModel: EntryViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            dashboard = it.getBoolean(ARG_DASHBOARD)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_vault_card, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dashboardBalance.setOnClickListener { showModal() }

        if (dashboard!!) {
            val chart = ChartFragment()
            val tx = childFragmentManager.beginTransaction()
            tx.replace(R.id.chartFragmentContainer, chart).commit()
            dashboardTransactions.visibility = if (dashboard!!) View.VISIBLE else View.GONE
        }

        menuButton.setOnClickListener { openDialog() }
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

    private fun openDialog() {
        menuDialog = MenuDialogFragment.create(vault)
        menuDialog.show(childFragmentManager, "dialog")
    }

    private fun showModal() {
        listDialog = VaultListDialogFragment.create(vaults)
        listDialog.listener = this
        listDialog.show(childFragmentManager, "dialog")
    }

    override fun onItemClick(item: Vault) {
        updateVault(item)
        listDialog.dismiss()
    }

    private fun updateVault(vault: Vault) {
        this.vault = vault
        dashboardVault.setText(vault.name)
        entryModel.getEntries(vault.id)
    }

    companion object {
        fun create(dashboard: Boolean): VaultCardFragment =
            VaultCardFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(ARG_DASHBOARD, dashboard)
                }
            }
    }
}
