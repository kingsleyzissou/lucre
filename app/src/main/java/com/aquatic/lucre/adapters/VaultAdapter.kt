package com.aquatic.lucre.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aquatic.lucre.R
import com.aquatic.lucre.models.Vault
import kotlinx.android.synthetic.main.card_vault.view.*

interface VaultListener {
    fun onVaultClick(Vault: Vault)
}

class VaultAdapter constructor(
    private var vaults: List<Vault>,
    private val listener: VaultListener
) : RecyclerView.Adapter<VaultAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VaultAdapter.MainHolder {
        return MainHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.card_vault,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: VaultAdapter.MainHolder, position: Int) {
        val vault = vaults[holder.adapterPosition]
        holder.bind(vault, listener)
    }

    override fun getItemCount(): Int = vaults.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(vault: Vault, listener: VaultListener) {
            itemView.title.text = vault.name
            itemView.description.text = vault.description
//            itemView.currency.text = vault.currency
            itemView.setOnClickListener { listener.onVaultClick(vault) }
        }
    }
}
