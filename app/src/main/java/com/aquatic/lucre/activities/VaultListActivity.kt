package com.aquatic.lucre.activities

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.aquatic.lucre.R
import com.aquatic.lucre.adapters.VaultAdapter
import com.aquatic.lucre.adapters.VaultListener
import com.aquatic.lucre.main.App
import com.aquatic.lucre.models.Vault
import kotlinx.android.synthetic.main.activity_vault_list.* // ktlint-disable no-wildcard-imports
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast

class VaultListActivity : AppCompatActivity(), VaultListener {
    lateinit var app: App

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vault_list)
        app = application as App

        toolbar.title = title
        setSupportActionBar(toolbar)

        toast("Store size: ${app.vaultStore.list.size}")

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = VaultAdapter(app.vaultStore.all(), this)
    }

    override fun onVaultClick(Vault: Vault) {
        startActivityForResult(intentFor<VaultActivity>(), 0)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_vault_list, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> startActivityForResult<VaultActivity>(0)
        }
        return super.onOptionsItemSelected(item)
    }
}
