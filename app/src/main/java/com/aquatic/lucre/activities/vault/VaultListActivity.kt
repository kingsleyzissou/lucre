package com.aquatic.lucre.activities.vault

import android.os.Bundle
import android.view.* // ktlint-disable no-wildcard-imports
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.aquatic.lucre.R
import com.aquatic.lucre.adapters.AdapterListener
import com.aquatic.lucre.adapters.VaultAdapter
import com.aquatic.lucre.main.App
import com.aquatic.lucre.models.Vault
import kotlinx.android.synthetic.main.activity_vault_list.* // ktlint-disable no-wildcard-imports
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivityForResult

class VaultListActivity : AppCompatActivity(), AdapterListener<Vault> {
    lateinit var app: App

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vault_list)
        app = application as App

        toolbar.title = title
        setSupportActionBar(toolbar)

        val layoutManager = LinearLayoutManager(this)
        vaultRecyclerView.layoutManager = layoutManager
        vaultRecyclerView.adapter = VaultAdapter(app.vaultStore.all(), this)
    }

    override fun onResume() {
        super.onResume()
        vaultRecyclerView.adapter = VaultAdapter(app.vaultStore.all(), this)
    }

    override fun onCardClick(vault: Vault) {
        startActivityForResult(
            intentFor<VaultActivity>().putExtra("vault_edit", vault),
            0
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_vault_list, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.vault_add -> startActivityForResult<VaultActivity>(0)
        }
        return super.onOptionsItemSelected(item)
    }
}
