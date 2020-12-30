package com.aquatic.lucre.activities.entry

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.aquatic.lucre.R
import com.aquatic.lucre.adapters.AdapterListener
import com.aquatic.lucre.adapters.EntryAdapter
import com.aquatic.lucre.main.App
import com.aquatic.lucre.models.Entry
import kotlinx.android.synthetic.main.activity_category_list.*
import kotlinx.android.synthetic.main.activity_entry_list.*
import kotlinx.android.synthetic.main.activity_vault_list.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivityForResult

class EntryListActivity : AppCompatActivity(), AdapterListener<Entry> {
    lateinit var app: App

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry_list)

        app = application as App

        entryListToolbar.title = title
        setSupportActionBar(entryListToolbar)

        val layoutManager = LinearLayoutManager(this)
        entryRecyclerView.layoutManager = layoutManager
        entryRecyclerView.adapter = EntryAdapter(app.entryStore.all(), this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        entryRecyclerView.adapter = EntryAdapter(app.entryStore.all(), this)
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onCardClick(entry: Entry) {
        startActivityForResult(
            intentFor<EntryActivity>().putExtra("entry_edit", entry),
            0
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_entry_list, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.entry_add -> startActivityForResult<EntryActivity>(0)
        }
        return super.onOptionsItemSelected(item)
    }
}
