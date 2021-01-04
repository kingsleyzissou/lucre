package com.aquatic.lucre.activities.dialogs

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.aquatic.lucre.R
import com.aquatic.lucre.activities.vault.VaultActivity
import com.aquatic.lucre.adapters.BottomSheetAdapter
import com.aquatic.lucre.core.AdapterListener
import com.aquatic.lucre.models.Vault
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_vault_list_dialog_list_dialog.*

const val ARG_VAULTS = "items"

/**
 *
 * A fragment that shows a list of items as a modal bottom sheet.
 *
 * You can show this modal bottom sheet from your activity like this:
 * <pre>
 *    VaultListDialogFragment.newInstance(30).show(supportFragmentManager, "dialog")
 * </pre>
 */
class VaultListDialogFragment : BottomSheetDialogFragment() {

    lateinit var listener: AdapterListener<Vault>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_vault_list_dialog_list_dialog, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        list.layoutManager = LinearLayoutManager(context)
        list.adapter = arguments?.getParcelableArrayList<Vault>(ARG_VAULTS)?.let { BottomSheetAdapter(it, listener) }
        addVault.setOnClickListener { switchActivity() }
    }

    private fun switchActivity() {
        startActivity(Intent(activity, VaultActivity::class.java))
    }

    companion object {
        fun create(vaults: List<Vault>): VaultListDialogFragment =
            VaultListDialogFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(ARG_VAULTS, vaults as ArrayList<Vault>)
                }
            }
    }
}
