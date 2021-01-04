package com.aquatic.lucre.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.aquatic.lucre.R
import com.aquatic.lucre.models.Vault

class BottomSheetAdapter constructor(
    var vaults: MutableList<Vault>,
    listener: AdapterListener<Vault>
) : BaseAdapter<Vault>(vaults, listener) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BottomSheetAdapter.MainHolder {
        return BottomSheetAdapter.MainHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.fragment_vault_list_dialog_list_dialog_item,
                parent,
                false
            )
        )
    }

    class MainHolder constructor(itemView: View) : BaseAdapter.MainHolder<Vault>(itemView) {

        override fun bind(value: Vault, listener: AdapterListener<Vault>) {
            val text: TextView = itemView.findViewById(R.id.text)
            text.text = "${value.name} (${value.currency})"
            itemView.setOnClickListener { listener.onItemClick(value) }
        }
    }
}
