package com.aquatic.lucre.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.aquatic.lucre.R
import com.aquatic.lucre.extensions.validate
import com.aquatic.lucre.main.App
import com.aquatic.lucre.models.Vault
import com.aquatic.lucre.viewmodels.VaultViewModel
import kotlinx.android.synthetic.main.activity_vault.* // ktlint-disable no-wildcard-imports
import org.jetbrains.anko.AnkoLogger

class VaultActivity : AppCompatActivity(), AnkoLogger {

    var vault = Vault()
    var options = mutableListOf<String>(
        "$", "£", "€", "AED", "R", "R$", "¥"
    )

    val model: VaultViewModel by viewModels()

    lateinit var currency: SpinnerActivity<String>
    lateinit var app: App

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vault)

        app = application as App

        handleIntent()

        currency = SpinnerActivity(this, vaultCurrency, options)

        vaultSubmit.setOnClickListener { submit() }
    }

    private fun handleIntent() {
        if (intent.hasExtra("vault_edit")) {
            vault = intent.extras?.getParcelable<Vault>("vault_edit")!!
            vaultName.setText(vault.name)
            vaultDescription.setText(vault.description)
            val index = options.indexOf(vault.currency)
            vaultCurrency.setSelection(index)
            vaultSubmit.setText(R.string.item_edit)
        }
    }

    private fun validate(): Boolean {
        val message = getResources().getString(R.string.required)
        return vaultName.validate(message) { it.isNotEmpty() }
    }

    private fun submit() {
        if (this.validate()) {
            vault.name = vaultName.text.toString()
            vault.description = vaultDescription.text.toString()
            vault.currency = currency.selection
            vault.userId = app.user?.id
            model.saveVault(vault.copy())
            setResult(AppCompatActivity.RESULT_OK)
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_vault_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.vault_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
