package com.aquatic.lucre.activities.ui

import android.R
import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.AnkoLogger

class SpinnerActivity<T> : AnkoLogger, AdapterView.OnItemSelectedListener {

    var selection: T? = null
    var spinner: Spinner
    var options: List<T>

    constructor(
        parent: AppCompatActivity,
        spinner: Spinner,
        options: List<T>,
        listener: AdapterView.OnItemSelectedListener? = null
    ) {
        this.spinner = spinner
        this.options = options
        spinner.onItemSelectedListener = listener ?: this
        val adapter = ArrayAdapter(parent, R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    constructor(
        parent: Context,
        spinner: Spinner,
        options: List<T>,
        listener: AdapterView.OnItemSelectedListener? = null
    ) {
        this.spinner = spinner
        this.options = options
        spinner.onItemSelectedListener = listener ?: this
        val adapter = ArrayAdapter(parent, R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    fun setSelectedItem(selection: T) {
        this.selection = selection
        val index = options.indexOf(selection)
        spinner.setSelection(index)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        this.selection = options[position]
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}
