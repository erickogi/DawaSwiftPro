package com.dev.dawaswift.ui.category.ui.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.dev.common.models.custom.Status
import com.dev.common.utils.OnRecyclerViewItemClick
import com.dev.common.utils.viewUtils.ViewUtils
import com.dev.dawaswift.R
import com.dev.dawaswift.adapters.healtharea.HealthAreaAdapter
import com.dev.dawaswift.models.category.HealthArea
import com.dev.dawaswift.ui.category.Categories
import kotlinx.android.synthetic.main.health_areas_fragment.*
import java.util.*

class HealthAreasFragment : Fragment() {

    companion object {
        fun newInstance() = HealthAreasFragment()
    }

    private lateinit var viewModel: CategoriesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.health_areas_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CategoriesViewModel::class.java)
        // TODO: Use the ViewModel
        initViewHealthAreas()

        viewModel.fetchHealthAreas()


        viewModel.observeHealthAreas().observe(this, androidx.lifecycle.Observer {
            ViewUtils.setStatus(
                activity,
                view,
                it.status,
                it.message,
                false,
                ViewUtils.ErrorViewTypes.TOAST,
                it.exception
            )
            if (it.status == Status.SUCCESS) {
                updateHealthAreas(it.data?.data)

            }

        })


    }

    private fun updateHealthAreas(data: List<HealthArea>?) {
        healthAreas=data
        healthAreaAdapter?.updateList(healthAreas)
    }


    private var healthAreaAdapter: HealthAreaAdapter? = null
    private var healthAreas:  List<HealthArea>?= LinkedList()

    private fun initViewHealthAreas() {

        healthAreaAdapter = context?.let {
            HealthAreaAdapter(it, healthAreas, object : OnRecyclerViewItemClick {
                override fun onClickListener(position: Int) {


                    val search = viewModel.fetchSearch()


                    search.healthAreaId = healthAreas!![position].id
                    search.healthAreaName = healthAreas!![position].name


                    viewModel.saveSearch(search)

                    (activity as Categories).onselection()
                }

                override fun onLongClickListener(position: Int) {

                    val search = viewModel.fetchSearch()
                    search.healthAreaId = healthAreas!![position].id
                    search.healthAreaName = healthAreas!![position].name


                    viewModel.saveSearch(search)

                    (activity as Categories).onselection()
                }
            })
        }!!
        val spanCount = 3
        val layoutManager = GridLayoutManager(context, spanCount)
        recyclerview_health_areas.layoutManager = layoutManager
        recyclerview_health_areas.itemAnimator = DefaultItemAnimator()
        recyclerview_health_areas.adapter = healthAreaAdapter
        healthAreaAdapter!!.notifyDataSetChanged()

    }


}
