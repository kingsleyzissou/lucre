package com.aquatic.lucre.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aquatic.lucre.R
import kotlinx.android.synthetic.main.activity_vault.*
import kotlinx.android.synthetic.main.activity_vault_list.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast

class VaultActivity : AppCompatActivity(), AnkoLogger {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vault)

        btnAdd.setOnClickListener() {
            val placemarkTitle = placemarkTitle.text.toString()
            if (placemarkTitle.isNotEmpty()) {
                info("add Button Pressed: $placemarkTitle")
            } else {
                toast("Please Enter a title")
            }
        }
    }
}
