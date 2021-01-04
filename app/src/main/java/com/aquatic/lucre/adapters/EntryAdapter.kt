package com.aquatic.lucre.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aquatic.lucre.R
import com.aquatic.lucre.core.AdapterListener
import com.aquatic.lucre.core.BaseAdapter
import com.aquatic.lucre.models.Entry
import kotlinx.android.synthetic.main.recycler_entry_list_item.view.*

class EntryAdapter constructor(
    entries: MutableList<Entry>,
    listener: AdapterListener<Entry>
) : BaseAdapter<Entry>(entries, listener) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryAdapter.MainHolder {
        return MainHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.recycler_entry_list_item,
                parent,
                false
            )
        )
    }

    class MainHolder constructor(itemView: View) : BaseAdapter.MainHolder<Entry>(itemView) {
        override fun bind(value: Entry, listener: AdapterListener<Entry>) {
            itemView.entryTitle.text = value.vendor
            itemView.entryAmount.text = "${value.signedAmount}  "
            itemView.entryDescription.text = value.description
            itemView.entryDate.text = value.date
            itemView.setOnClickListener { listener.onItemClick(value) }
        }
    }
}
