package com.aquatic.lucre.activities

import android.R
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.AnkoLogger

class SpinnerActivity<T>: AnkoLogger, AdapterView.OnItemSelectedListener {
    var selection: String? = null
    var options: List<T>? = null

    constructor(
        parent: AppCompatActivity,
        spinner: Spinner,
        options: List<T>
    ) {
        this.options = options
        // https://www.tutorialkart.com/kotlin-android/android-spinner-kotlin-example/
        spinner.setOnItemSelectedListener(this)
        val array_adapter = ArrayAdapter(parent, R.layout.simple_spinner_item, options)
        array_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner!!.setAdapter(array_adapter)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        selection = options!![position].toString()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}