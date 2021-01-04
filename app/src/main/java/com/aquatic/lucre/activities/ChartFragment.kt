package com.aquatic.lucre.activities

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.replace
import androidx.lifecycle.Observer
import com.aquatic.lucre.R
import com.aquatic.lucre.viewmodels.CategoryViewModel
import com.aquatic.lucre.viewmodels.EntryViewModel
import kotlinx.android.synthetic.main.fragment_chart.*
import org.eazegraph.lib.models.PieModel
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

/**
 * A simple [Fragment] subclass.
 * Use the [ChartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChartFragment : Fragment(), AnkoLogger {

    val model: EntryViewModel by activityViewModels()
    val categoryModel: CategoryViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_chart, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val child = ChartCategoryFragment()
        val tx = childFragmentManager.beginTransaction()
        tx.replace(R.id.childFragmentContainer, child).commit()
        observeStore()
    }

    fun observeStore() {
        categoryModel.list.observe(
            viewLifecycleOwner,
            Observer { categories ->

                model.list.observe(
                    viewLifecycleOwner,
                    Observer { entries ->
                        info("We got entries: $entries")

                        val s = model.expenseCategories(entries, categories)
                        s.forEach { k, v ->
                            info("${k.color}, $v")
                            val pieModel = PieModel(
                                k.name,
                                v,
                                Color.parseColor(k.color)
                            )
                            piechart.addPieSlice(pieModel)
                        }
                        piechart.startAnimation()
                    }
                )
            }
        )
    }
}
