package com.aquatic.lucre.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aquatic.lucre.R
import com.aquatic.lucre.models.Vault
import kotlinx.android.synthetic.main.card_vault.view.*

class VaultAdapter constructor(private var vaults: List<Vault>) : RecyclerView.Adapter<VaultAdapter.MainHolder>() {

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
        holder.bind(vault)
    }

    override fun getItemCount(): Int = vaults.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(vault: Vault) {
            itemView.title.text = vault.name
            itemView.description.text = vault.description
        }
    }
}
