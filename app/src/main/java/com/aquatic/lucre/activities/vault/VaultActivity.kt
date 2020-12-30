package com.aquatic.lucre.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.aquatic.lucre.R
import com.aquatic.lucre.extensions.validate
import com.aquatic.lucre.main.App
import com.aquatic.lucre.models.Vault
import kotlinx.android.synthetic.main.activity_vault.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast

class VaultActivity : AppCompatActivity(), AnkoLogger, AdapterView.OnItemSelectedListener {

    var vault = Vault()
    var name: String? = null
    var description: String? = null
    var currency: String? = null
    var options = listOf<String>(
        "$", "£", "€", "AED", "R", "R$", "¥"
    )

    lateinit var app: App

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vault)

        app = application as App

        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)

        if (intent.hasExtra("vault_edit")) {
            vault = intent.extras?.getParcelable<Vault>("vault_edit")!!
            vaultName.setText(vault.name)
            vaultDescription.setText(vault.description)
            val index = options.indexOf(vault.currency)
            vaultSpinner.setSelection(index)
        }

        // https://www.tutorialkart.com/kotlin-android/android-spinner-kotlin-example/
        vaultSpinner.setOnItemSelectedListener(this)
        val array_adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, options)
        array_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        vaultSpinner!!.setAdapter(array_adapter)

        btnAdd.setOnClickListener { submit() }
    }

    private fun validate(): Boolean {
        return vaultName.validate("Name is required") { it.isNotEmpty() }
    }

    private fun submit() {
        if (this.validate()) {
            vault.name = vaultName.text.toString()
            vault.description = vaultDescription.text.toString()
            vault.currency = currency
            toast("Vault created: $vault")
            app.vaultStore.create(vault.copy())
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
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {
        currency = options[position]
    }

    override fun onNothingSelected(arg0: AdapterView<*>) {
        TODO("Not implemented yet")
    }
}
