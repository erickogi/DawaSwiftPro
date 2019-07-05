package com.dev.dawaswift.ui.category.ui.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.common.data.Constants
import com.dev.common.models.custom.Status
import com.dev.common.utils.OnRecyclerViewItemClick
import com.dev.common.utils.viewUtils.ViewUtils
import com.dev.dawaswift.R
import com.dev.dawaswift.adapters.category.CategoryAdapter
import com.dev.dawaswift.models.category.Category
import com.dev.dawaswift.ui.category.Categories
import kotlinx.android.synthetic.main.categories_fragment.*
import java.util.*

class CategoriesFragment : Fragment() {

    companion object {
        fun newInstance() = CategoriesFragment()
    }

    private lateinit var viewModel: CategoriesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.categories_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CategoriesViewModel::class.java)
        initCategories()

        viewModel.fetchCategories()


        viewModel.observeCategories().observe(this, Observer {

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

                updateCategories(it.data?.data)

            }



        })

    }
    private fun updateCategories(data: List<Category>?) {

        categories=data
        categoriesAdapter?.updateList(categories)
    }

    private var categories: List<Category>?= LinkedList()
    private var categoriesAdapter: CategoryAdapter? = null
    private fun initCategories() {

        categoriesAdapter = context?.let {
            CategoryAdapter(Constants().LINEAR,it, categories, object : OnRecyclerViewItemClick {
                override fun onClickListener(position: Int) {

                    var fragment=SubCategoriesFragment()
                    fragment
                    .arguments= Bundle().apply {
                        putSerializable(Constants().CATEGORY, categories?.get(position))
                    }
                    activity!!.supportFragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack("SUB").commit()


                }

                override fun onLongClickListener(position: Int) {

                    val search = viewModel.fetchSearch()

                    search.subCategoryId = null
                    search.subcategoryName = null

                    search.subCategoryItemId = null
                    search.subcategoryItemName = null


                    search.categoryId = categories!![position].id
                    search.categoryName = categories!![position].name

                    viewModel.saveSearch(search)


                    (activity as Categories).onselection()

                }
            })
        }!!

        recyclerview_categories.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerview_categories.itemAnimator = DefaultItemAnimator()
        recyclerview_categories.adapter = categoriesAdapter
        categoriesAdapter!!.notifyDataSetChanged()

    }


}
