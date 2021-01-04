package com.aquatic.lucre.activities

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import com.aquatic.lucre.R
import com.aquatic.lucre.models.Vault
import com.aquatic.lucre.viewmodels.VaultViewModel
import kotlinx.android.synthetic.main.fragment_menu_dialog.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

// TODO: Customize parameter argument names
const val ARG_VAULT = "vault"

/**
 *
 * A fragment that shows a list of items as a modal bottom sheet.
 *
 * You can show this modal bottom sheet from your activity like this:
 * <pre>
 *    MenuDialogFragment.newInstance(30).show(supportFragmentManager, "dialog")
 * </pre>
 */
class MenuDialogFragment : BottomSheetDialogFragment(), AnkoLogger {

    var vault: Vault = Vault()

    val model: VaultViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            vault = it.getParcelable<Vault>(ARG_VAULT)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_menu_dialog, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addVault.setOnClickListener { switchActivity("add") }
        editVault.setOnClickListener { switchActivity("edit") }
        deleteVault.setOnClickListener { switchActivity("delete") }
        cancelAction.setOnClickListener { switchActivity("cancel") }
    }

    private fun switchActivity(action: String) {
        when (action) {
            "add" -> startActivity(Intent(activity, VaultActivity::class.java))
            "edit" -> startActivityForResult(Intent(activity, VaultActivity::class.java).putExtra("vault_edit", vault), 0)
            "delete" -> openDeleteDialog()
            "cancel" -> dismiss()
        }
    }

    private fun openDeleteDialog() {
        AlertDialog.Builder(context)
            .setTitle("Delete vault")
            .setMessage("Are you sure you want to delete this vault?")
            .setCancelable(true)
            .setNegativeButton(android.R.string.no, null)
            .setPositiveButton(android.R.string.yes) { dlg, i ->
                model.deleteVault(vault)
                dismiss()
            }
            .show()
    }
    
    companion object {
        fun create(vault: Vault): MenuDialogFragment =
            MenuDialogFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_VAULT, vault)
                }
            }

    }
}