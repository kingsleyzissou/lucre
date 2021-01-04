package com.aquatic.lucre.activities.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.aquatic.lucre.R
import com.aquatic.lucre.activities.vault.VaultCardFragment
import com.aquatic.lucre.viewmodels.UserViewModel
import kotlinx.android.synthetic.main.fragment_dashboard.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

/**
 * A simple [Fragment] subclass.
 * Use the [DashboardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DashboardFragment : Fragment(), AnkoLogger {

    val model: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_dashboard, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model.getSignedInUser()
        getUser()

        val child = VaultCardFragment.create(true)
        val tx = childFragmentManager.beginTransaction()
        tx.replace(R.id.childFragmentContainer, child)
        tx.commit()
    }

    private fun getUser() {
        model.signedInUser.observe(
            viewLifecycleOwner,
            Observer {
                info(it)
                dashboardName.setText("${it?.username}!")
            }
        )
    }
}
