package com.aquatic.lucre.activities.vault

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.aquatic.lucre.R
import com.aquatic.lucre.activities.dialogs.MenuDialogFragment
import com.aquatic.lucre.activities.dialogs.VaultListDialogFragment
import com.aquatic.lucre.activities.ui.ChartFragment
import com.aquatic.lucre.core.AdapterListener
import com.aquatic.lucre.models.Entry
import com.aquatic.lucre.models.Vault
import com.aquatic.lucre.utilities.ARG_DASHBOARD
import com.aquatic.lucre.viewmodels.EntryViewModel
import com.aquatic.lucre.viewmodels.VaultViewModel
import kotlinx.android.synthetic.main.fragment_vault_card.*
import org.jetbrains.anko.AnkoLogger

class VaultCardFragment : Fragment(), AdapterListener<Vault>, AnkoLogger {

    /* Flag for item being displayed in dashboard */
    var dashboard: Boolean? = null

    /* Empty vault object */
    var vault: Vault = Vault()

    /* Default vault balance */
    var balance: Float = 0F

    /* List of vaults */
    var vaults: MutableList<Vault> = ArrayList()

    /* List of entrries */
    var entries: MutableList<Entry> = ArrayList()

    /* Bottom sheet dialog for vault CRUD operations */
    private lateinit var menuDialog: MenuDialogFragment

    /* Bottom sheet for changing vault selection */
    private lateinit var listDialog: VaultListDialogFragment

    /* Category ViewModel by injection */
    private val vaultModel: VaultViewModel by activityViewModels()

    /* Category ViewModel by injection */
    private val entryModel: EntryViewModel by activityViewModels()

    /**
     * Override function for creating object with
     * additional parameters (in this case for the dashboard flag)
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            dashboard = it.getBoolean(ARG_DASHBOARD)
        }
    }

    /**
     * Inflate the view
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_vault_card, container, false)

    /**
     * Setup the fragment
     */
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

    /**
     * Observe the category list live data
     * and update the recycler view if there
     * are any changes
     */
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

    /**
     * Open the vault CRUD dialog
     */
    private fun openDialog() {
        menuDialog = MenuDialogFragment.create(vault)
        menuDialog.show(childFragmentManager, "dialog")
    }

    /**
     * Open the vault selection dialog
     */
    private fun showModal() {
        listDialog = VaultListDialogFragment.create(vaults)
        listDialog.listener = this
        listDialog.show(childFragmentManager, "dialog")
    }

    /**
     * Event listener for when a vault item is pressed.
     * This click event takes the user to the vault
     * edit activity
     */
    override fun onItemClick(item: Vault) {
        updateVault(item)
        listDialog.dismiss()
    }

    /**
     * Update the currently selected vault
     */
    private fun updateVault(vault: Vault) {
        this.vault = vault
        dashboardVault.setText(vault.name)
        entryModel.getEntries(vault.id)
    }

    /**
     * Companion object for instantiating the Card Fragment
     * with a boolean flag for when the widget is being
     * displayed on the dashboard screen
     */
    companion object {
        fun create(dashboard: Boolean): VaultCardFragment =
            VaultCardFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(ARG_DASHBOARD, dashboard)
                }
            }
    }
}
