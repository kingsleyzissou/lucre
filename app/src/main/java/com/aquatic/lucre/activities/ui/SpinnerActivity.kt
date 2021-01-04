package com.aquatic.lucre.activities.ui

import android.R
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.AnkoLogger

class SpinnerActivity<T>(
    parent: AppCompatActivity,
    var spinner: Spinner,
    var options: List<T>,
    listener: AdapterView.OnItemSelectedListener? = null
) : AnkoLogger, AdapterView.OnItemSelectedListener {

    /* Currently selected item */
    var selection: T? = null

    /**
     * Load the spinner options on
     * initiation
     */
    init {
        spinner.onItemSelectedListener = listener ?: this
        val adapter = ArrayAdapter(parent, R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    /**
     * Helper function to set the
     * selected item
     */
    fun setSelectedItem(selection: T) {
        this.selection = selection
        val index = options.indexOf(selection)
        spinner.setSelection(index)
    }

    /**
     * Handle the event when an item is selected
     */
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        this.selection = options[position]
    }

    /**
     * Do nothing, required for AdapterView.OnItemSelectedListener interface
     */
    override fun onNothingSelected(parent: AdapterView<*>?) {}
}
