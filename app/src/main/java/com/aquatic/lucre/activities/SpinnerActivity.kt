package com.aquatic.lucre.activities

import android.R
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class SpinnerActivity<T>(
    parent: AppCompatActivity,
    var spinner: Spinner,
    var options: List<T>
) : AnkoLogger, AdapterView.OnItemSelectedListener {

    var selection: T? = null

    init {
        // https://www.tutorialkart.com/kotlin-android/android-spinner-kotlin-example/
        spinner.setOnItemSelectedListener(this)
        val array_adapter = ArrayAdapter(parent, R.layout.simple_spinner_item, options)
        array_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.setAdapter(array_adapter)
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
