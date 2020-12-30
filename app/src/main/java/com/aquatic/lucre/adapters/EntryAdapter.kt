package com.aquatic.lucre.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aquatic.lucre.R
import com.aquatic.lucre.models.Entry
import kotlinx.android.synthetic.main.card_vault.view.*

class EntryAdapter constructor(
    private var entries: List<Entry>,
    private val listener: AdapterListener<Entry>
) : RecyclerView.Adapter<EntryAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryAdapter.MainHolder {
        return MainHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.card_entry,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: EntryAdapter.MainHolder, position: Int) {
        val entry = entries[holder.adapterPosition]
        holder.bind(entry, listener)
    }

    override fun getItemCount(): Int = entries.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(entry: Entry, listener: AdapterListener<Entry>) {
            itemView.title.text = entry.vendor
            itemView.description.text = entry.description
//            itemView.currency.text = vault.currency
            itemView.setOnClickListener { listener.onCardClick(entry) }
        }
    }
}
