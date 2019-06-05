package com.dev.dawaswift.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.badge.BadgeView
import com.dev.common.data.Constants
import com.dev.common.models.custom.Status
import com.dev.common.utils.OnRecyclerViewItemClick
import com.dev.common.utils.viewUtils.ViewUtils
import com.dev.dawaswift.R
import com.dev.dawaswift.SplashActivity
import com.dev.dawaswift.adapters.category.CategoryAdapter
import com.dev.dawaswift.adapters.healtharea.HealthAreaAdapter
import com.dev.dawaswift.adapters.product.ProductAdapter
import com.dev.dawaswift.models.Product.Product
import com.dev.dawaswift.models.Product.ProductSearchAndFilter
import com.dev.dawaswift.models.Product.ProductsResponse
import com.dev.dawaswift.models.category.Category
import com.dev.dawaswift.models.category.HealthArea
import com.dev.dawaswift.ui.category.Categories
import com.dev.dawaswift.ui.productsearch.ProductSearch
import com.dev.dawaswift.ui.productview.ProductView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.main_fragment.*
import java.util.*


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        BadgeView(context).bindTarget(cart).badgeText = "2"

        initCategories()
        initViewHealthAreas()
        initPopularProducts()

        viewModel.fetchCategories()
        viewModel.fetchHealthAreas()
        viewModel.fetchPopularProducts(ProductSearchAndFilter())


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
                    Log.d("DATAONLOADMESSAGE",""+ Gson().toJson(it.data))

                    updateCategories(it.data?.data)

                }



        })
        viewModel.observeHealthAreas().observe(this, Observer {
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
        viewModel.observePopularProducts().observe(this, Observer {
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

                updatePopularProducts(it.data?.data)
            }

        })

        txtViewCategories.setOnClickListener{
            val intent=Intent(activity,Categories::class.java)
            intent.putExtra(Constants().RESOURCE,Constants().CATEGORIES)
            startActivity(intent)
        }
        txtViewHealthAreas.setOnClickListener{
            val intent=Intent(activity,Categories::class.java)
            intent.putExtra(Constants().RESOURCE,Constants().HEALTHAREAS)
            startActivity(intent)        }

        search_view.setOnClickListener { startActivity(Intent(activity, ProductSearch::class.java)) }



    }

    private fun updatePopularProducts(data: List<Product>?) {
        popularProducts=data
        productsAdapter?.updateList(popularProducts)
    }

    private fun updateHealthAreas(data: List<HealthArea>?) {
        healthAreas=data
        healthAreaAdapter?.updateList(healthAreas)
    }

    private fun updateCategories(data: List<Category>?) {

        categories=data
        categoriesAdapter?.updateList(categories)
    }

    private var categories: List<Category>?=LinkedList()
    private var categoriesAdapter: CategoryAdapter? = null
    private fun initCategories() {

        categoriesAdapter = context?.let {
            CategoryAdapter(Constants().HORIZONTAL,it, categories, object : OnRecyclerViewItemClick {
                override fun onClickListener(position: Int) {

                }

                override fun onLongClickListener(position: Int) {
                }
            })
        }!!


        recyclerview_categories.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerview_categories.itemAnimator = DefaultItemAnimator()
        recyclerview_categories.adapter = categoriesAdapter
        categoriesAdapter!!.notifyDataSetChanged()

    }


    private var healthAreaAdapter: HealthAreaAdapter? = null
    private var healthAreas:  List<HealthArea>?=LinkedList()

    private fun initViewHealthAreas() {

        healthAreaAdapter = context?.let {
            HealthAreaAdapter(it, healthAreas, object : OnRecyclerViewItemClick {
                override fun onClickListener(position: Int) {

                }

                override fun onLongClickListener(position: Int) {
                }
            })
        }!!
        recyclerview_health_areas.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerview_health_areas.itemAnimator = DefaultItemAnimator()
        recyclerview_health_areas.adapter = healthAreaAdapter
        healthAreaAdapter!!.notifyDataSetChanged()

    }


    private var productsAdapter: ProductAdapter? = null
    private var popularProducts:  List<Product>?=LinkedList()
    private fun initPopularProducts() {

        productsAdapter = context?.let {
            ProductAdapter(it, popularProducts, object : OnRecyclerViewItemClick {
                override fun onClickListener(position: Int) {

                   val intent= Intent(activity,ProductView::class.java)
                    intent.putExtra(Constants().PRODUCT,popularProducts?.get(position))
                    startActivity(intent)

                }

                override fun onLongClickListener(position: Int) {
                    var intent= Intent(activity,ProductView::class.java)
                    intent.putExtra(Constants().PRODUCT,popularProducts?.get(position))
                    startActivity(intent)
                }
            })
        }!!
        val spanCount = 2
        val layoutManager = GridLayoutManager(context, spanCount)
        recyclerview_popular_products.layoutManager = layoutManager
        recyclerview_popular_products.itemAnimator = DefaultItemAnimator()
        recyclerview_popular_products.adapter = productsAdapter
        productsAdapter!!.notifyDataSetChanged()

    }


}
