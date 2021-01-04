package com.aquatic.lucre.activities.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aquatic.lucre.R
import com.aquatic.lucre.activities.vault.VaultCardFragment
import com.aquatic.lucre.models.User
import kotlinx.android.synthetic.main.fragment_dashboard.*

/**
 * A simple [Fragment] subclass.
 * Use the [DashboardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DashboardFragment : Fragment() {

    private lateinit var user: User

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_dashboard, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        user = User()
        user.username = "Kingsley"
        dashboardName.setText("${user.username}!")

        val child = VaultCardFragment.create(true)
        val tx = childFragmentManager.beginTransaction()
        tx.replace(R.id.childFragmentContainer, child)
        tx.commit()
    }
}
