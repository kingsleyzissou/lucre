package com.aquatic.lucre.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aquatic.lucre.R
import com.aquatic.lucre.models.Vault
import kotlinx.android.synthetic.main.card_vault.view.*

class VaultAdapter constructor(
    var vaults: MutableList<Vault>,
    listener: AdapterListener<Vault>
) : BaseAdapter<Vault>(vaults, listener) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VaultAdapter.MainHolder {
        return MainHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.card_vault,
                parent,
                false
            )
        )
    }

    class MainHolder constructor(itemView: View) : BaseAdapter.MainHolder<Vault>(itemView) {
        override fun bind(value: Vault, listener: AdapterListener<Vault>) {
            itemView.title.text = value.name
            itemView.description.text = "${ value.description } (${ value.currency })"
            itemView.setOnClickListener { listener.onItemClick(value) }
        }
    }
}
