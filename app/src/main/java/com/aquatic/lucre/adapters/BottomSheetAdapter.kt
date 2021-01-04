package com.aquatic.lucre.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.aquatic.lucre.R
import com.aquatic.lucre.core.AdapterListener
import com.aquatic.lucre.core.BaseAdapter
import com.aquatic.lucre.models.Vault

class BottomSheetAdapter constructor(
    var vaults: MutableList<Vault>,
    listener: AdapterListener<Vault>
) : BaseAdapter<Vault>(vaults, listener) {

    /**
     * The implementation for each adapter is
     * slightly different and this is left to
     * the concrete classes
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BottomSheetAdapter.MainHolder {
        return BottomSheetAdapter.MainHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.recycler_vault_list_dialog_item,
                parent,
                false
            )
        )
    }

    /**
     * View holder for the bottom sheet recycler view
     */
    class MainHolder constructor(itemView: View) : BaseAdapter.MainHolder<Vault>(itemView) {
        override fun bind(value: Vault, listener: AdapterListener<Vault>) {
            val text: TextView = itemView.findViewById(R.id.text)
            text.text = "${value.name} (${value.currency})"
            itemView.setOnClickListener { listener.onItemClick(value) }
        }
    }
}
