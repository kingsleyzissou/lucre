package com.aquatic.lucre.activities.vault

import android.app.AlertDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.aquatic.lucre.R
import com.aquatic.lucre.activities.ui.SpinnerActivity
import com.aquatic.lucre.extensions.validate
import com.aquatic.lucre.main.App
import com.aquatic.lucre.models.Vault
import com.aquatic.lucre.viewmodels.VaultViewModel
import kotlinx.android.synthetic.main.activity_vault.* // ktlint-disable no-wildcard-imports
import org.jetbrains.anko.AnkoLogger

class VaultActivity : AppCompatActivity(), AnkoLogger {

    /* Empty vault object */
    var vault = Vault()

    /* Currency options for spinner */
    var options = mutableListOf<String>(
        "$", "£", "€", "AED", "R", "R$", "¥"
    )

    /* Android application object */
    val model: VaultViewModel by viewModels()

    /* Spinner for currency */
    lateinit var currency: SpinnerActivity<String>

    /* Android application object */
    lateinit var app: App

    /**
     * Setup the activity
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vault)

        app = application as App

        handleIntent()

        currency = SpinnerActivity(this, vaultCurrency, options)

        vaultSubmit.setOnClickListener { submit() }
        vaultDelete.setOnClickListener { openDeleteDialog() }
    }

    /**
     * If there is an intent, we need to handle this
     * and set the associated data
     */
    private fun handleIntent() {
        if (intent.hasExtra("vault_edit")) {
            vault = intent.extras?.getParcelable<Vault>("vault_edit")!!
            vaultName.setText(vault.name)
            vaultDescription.setText(vault.description)
            val index = options.indexOf(vault.currency)
            vaultCurrency.setSelection(index)
            vaultSubmit.setText(R.string.item_edit)
            vaultDelete.visibility = View.VISIBLE
        }
    }

    /**
     * Validate the EditText fields
     * to make sure all inputs are
     * valid
     */
    private fun validate(): Boolean {
        val message = getResources().getString(R.string.required)
        return vaultName.validate(message) { it.isNotEmpty() }
    }

    /**
     * Submit the vault create/update request
     */
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

    /**
     * Programmatically open the delete category
     * dialog
     */
    private fun openDeleteDialog() {
        AlertDialog.Builder(this)
            .setTitle("Delete vault")
            .setMessage("Are you sure you want to delete this vault?")
            .setCancelable(true)
            .setNegativeButton(android.R.string.cancel, null)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                model.deleteVault(vault)
                finish()
            }
            .show()
    }

    /**
     * Add the cancel options menu for the activity
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_vault_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    /**
     * Return to previous activity if activity is
     * cancelled
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.vault_cancel -> { finish() }
        }
        return super.onOptionsItemSelected(item)
    }
}
