package com.aquatic.lucre.activities

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aquatic.lucre.R
import com.aquatic.lucre.main.App
import com.aquatic.lucre.models.Vault
import kotlinx.android.synthetic.main.activity_vault_list.*
import kotlinx.android.synthetic.main.card_vault.view.*
import org.jetbrains.anko.startActivityForResult

class VaultListActivity : AppCompatActivity() {
    lateinit var app: App

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vault_list)
        app = application as App

        toolbar.title = title
        setSupportActionBar(toolbar)

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = VaultAdapter(app.vaultStore.all())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_vault, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> startActivityForResult<VaultActivity>(0)
        }
        return super.onOptionsItemSelected(item)
    }
}

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
