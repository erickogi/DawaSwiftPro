package com.dev.dawaswift.ui.productsearch

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.dev.common.data.Constants
import com.dev.common.models.ProductSearchAndFilter
import com.dev.common.models.custom.SearchModel
import com.dev.common.models.custom.Status
import com.dev.common.models.location.LocationSearchModel
import com.dev.common.utils.CommonUtils
import com.dev.common.utils.OnRecyclerViewItemClick
import com.dev.common.utils.SearchDialogListener
import com.dev.common.utils.viewUtils.ViewUtils
import com.dev.dawaswift.R
import com.dev.dawaswift.adapters.product.ProductAdapter
import com.dev.dawaswift.models.Product.Product
import com.dev.dawaswift.models.Product.Tags
import com.dev.dawaswift.models.category.HealthArea
import com.dev.dawaswift.models.pharmacy.Pharmacy
import com.dev.dawaswift.ui.CommonMainViewModel
import com.dev.dawaswift.ui.category.Categories
import com.dev.dawaswift.ui.productview.ProductView
import kotlinx.android.synthetic.main.product_search_activity.*
import java.util.*
import kotlin.collections.ArrayList

class ProductSearch : AppCompatActivity(), AdapterView.OnItemClickListener {
    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

        if (tags != null) {

            val search = viewModel.fetchSearch()

            val tag = tags!![p2]
            if (tag.typeId == Constants().products) {
                search.productId = tag.id
            }
            if (tag.typeId == Constants().category) {
                search.categoryId = tag.id
            }
            if (tag.typeId == Constants().subcategory) {
                search.subCategoryId = tag.id
            }
            if (tag.typeId == Constants().subcategoryitem) {
                search.subCategoryItemId = tag.id
            }
            if (tag.typeId == Constants().heaalthareas) {
                search.healthAreaId = tag.id
            }
            viewModel.saveSearch(search)
        }
    }

    var tags: List<Tags>? = null

    private lateinit var viewModel: CommonMainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.product_search_activity)

        linear_back.setOnClickListener {
            onBackPressed()
        }
        viewModel = ViewModelProviders.of(this).get(CommonMainViewModel::class.java)


        initPopularProducts()
        viewModel.observeProducts().observe(this, Observer {
            ViewUtils.setStatus(
                this,
                card_products,
                it.status,
                it.message,
                false,
                ViewUtils.ErrorViewTypes.TOAST,
                it.exception
            )
            if (it.status == Status.SUCCESS) {

                updatePopularProducts(it.data?.data)
            } else {
                updatePopularProducts(ArrayList())
            }

        })


        searchProducts()

        viewModel.observeTags().observe(this, Observer {

            if (it.status == Status.SUCCESS && it.data != null) {
                tags = it!!.data?.data
                val array = arrayOfNulls<String>(tags!!.size)



                for (item in tags!!.indices) {
                    // printing array elements

                    array[item] = tags!![item].name


                }
                val adapter = ArrayAdapter(this, android.R.layout.select_dialog_item, array)

                search.setAdapter(adapter)
                search.threshold = 1

                search.onItemClickListener = this
            }


        })
        viewModel.tags()



        viewModel.fetchHealthAreas()
        viewModel.observeHealthAreas().observe(this, androidx.lifecycle.Observer {
            ViewUtils.setStatus(
                this,
                null,
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



        viewModel.pharmacies(LocationSearchModel())
        viewModel.observePhamacies().observe(this, androidx.lifecycle.Observer {

            ViewUtils.setStatus(
                this,
                null,
                it.status,
                it.message,
                false,
                ViewUtils.ErrorViewTypes.TOAST,
                it.exception
            )
            if (it.status == Status.SUCCESS) {

                updatePhamacies(it.data?.data)

            }


        })



        searchIcon.setOnClickListener {

            if (!TextUtils.isEmpty(search.text)) {
                val searcsh = viewModel.fetchSearch()

                searcsh.queryString = search.text.toString()
                viewModel.saveSearch(searcsh)


            }

        }

        healtharea.setOnClickListener {


            if (healthAreas != null) {
                healtharea.isCloseIconEnabled = true

                val <T> searchModels = ArrayList<SearchModel<HealthArea>>()

                val subcategoriesItems = healthAreas


                for (subcategories in subcategoriesItems as List) {
                    searchModels.add(
                        SearchModel(
                            title = subcategories.name!!,
                            id = "" + subcategories.id!!,
                            serices = subcategories
                        )
                    )
                }
                CommonUtils().searchDialog(
                    this, "Search...", "Search ",
                    searchModels, true, object :
                        SearchDialogListener<HealthArea> {
                        override fun onResults(
                            item: SearchModel<HealthArea>?,
                            position: Int
                        ) {

                            val search = viewModel.fetchSearch()
                            search.healthAreaId = item?.getServices()?.id
                            search.healthAreaName = item?.getServices()?.name
                            viewModel.saveSearch(search)


                        }
                    })
            }
        }
        healtharea.setOnCloseIconClickListener {

            val search = viewModel.fetchSearch()
            search.healthAreaId = null
            search.healthAreaName = null
            viewModel.saveSearch(search)

            healtharea.isChecked = false
            healtharea.isCloseIconEnabled = false


        }



        pharmacy.setOnClickListener {


            if (pharmacies != null) {
                pharmacy.isCloseIconEnabled = true

                val <T> searchModels = ArrayList<SearchModel<Pharmacy>>()

                val subcategoriesItems = pharmacies


                for (subcategories in subcategoriesItems as List) {
                    searchModels.add(
                        SearchModel(
                            title = subcategories.name!!,
                            id = "" + subcategories.id!!,
                            serices = subcategories
                        )
                    )
                }
                CommonUtils().searchDialog(
                    this, "Search...", "Search ",
                    searchModels, true, object :
                        SearchDialogListener<Pharmacy> {
                        override fun onResults(
                            item: SearchModel<Pharmacy>?,
                            position: Int
                        ) {

                            val search = viewModel.fetchSearch()
                            search.pharmacyId = item?.getServices()?.id
                            search.pharmacyName = item?.getServices()?.name
                            viewModel.saveSearch(search)


                        }
                    })
            }
        }
        pharmacy.setOnCloseIconClickListener {

            val search = viewModel.fetchSearch()
            search.pharmacyId = null
            search.pharmacyName = null
            viewModel.saveSearch(search)

            pharmacy.isChecked = false
            pharmacy.isCloseIconEnabled = false


        }




        category.setOnClickListener {

            viewModel.saveSearch(ProductSearchAndFilter())
            val intent = Intent(this, Categories::class.java)
            intent.putExtra(Constants().RESOURCE, Constants().CATEGORIES)
            intent.putExtra(Constants().CATEGORYACTION, Constants().DONOTING)
            startActivity(intent)

        }
        category.setOnCloseIconClickListener {

            val search = viewModel.fetchSearch()
            search.categoryId = null
            search.categoryName = null
            viewModel.saveSearch(search)

            category.isChecked = false
            category.isCloseIconEnabled = false


        }








        category.setOnCloseIconClickListener {

            val search = viewModel.fetchSearch()
            search.categoryId = null
            search.categoryName = null

            search.subCategoryId = null
            search.subcategoryName = null

            search.subCategoryItemId = null
            search.subcategoryItemName = null


            viewModel.saveSearch(search)

            category.isChecked = false
            category.isCloseIconEnabled = false


        }

        pharmacy.setOnCloseIconClickListener {

            val search = viewModel.fetchSearch()
            search.pharmacyId = null
            viewModel.saveSearch(search)

            pharmacy.isChecked = false
            pharmacy.isCloseIconEnabled = false


        }




        clearfilters.setOnClickListener {

            category.isChecked = false
            healtharea.isChecked = false
            pharmacy.isChecked = false
            search.setText("")

            category.isCloseIconVisible = false
            healtharea.isCloseIconVisible = false
            pharmacy.isCloseIconVisible = false

            viewModel.saveSearch(ProductSearchAndFilter())
        }
    }

    private fun searchProducts() {

        viewModel.getSearch().observe(this, Observer {

            if (it != null) {

                if (it.categoryId == null && it.subCategoryId == null && it.subCategoryItemId == null) {
                    category.text = "Categories"
                    category.isCloseIconEnabled = false


                } else {
                    if (it.categoryName != null) {
                        category.isCloseIconEnabled = true
                        category.text = "Category :\n" + it.categoryName

                    }

                    if (it.subcategoryName != null) {
                        category.isCloseIconEnabled = true
                        category.text = "Category :\n" + it.subcategoryName

                    }

                    if (it.subcategoryItemName != null) {
                        category.isCloseIconEnabled = true
                        category.text = "Category :\n" + it.subcategoryItemName

                    }
                }













                if (it.pharmacyId == null) {
                    pharmacy.text = "Pharmacies"
                    pharmacy.isCloseIconEnabled = false


                } else {
                    if (it.pharmacyName != null) {
                        pharmacy.isCloseIconEnabled = true

                        pharmacy.text = "Pharmacy :\n" + it.pharmacyName

                    }
                }




                if (it.healthAreaId == null) {
                    healtharea.text = "Health Areas"
                    healtharea.isCloseIconEnabled = false


                } else {
                    if (it.healthAreaName != null) {
                        healtharea.isCloseIconEnabled = true

                        healtharea.text = "Health Area :\n" + it.healthAreaName

                    }
                }




                if (it.categoryId != null || it.productId != null || it.healthAreaId != null || it.subCategoryId != null || it.subCategoryItemId != null || it.queryString != null) {
                    clearfilters.visibility = View.VISIBLE

                } else {
                    clearfilters.visibility = View.GONE

                }



                viewModel.fetchProducts(it)
            } else {
                clearfilters.visibility = View.GONE
                viewModel.fetchProducts(ProductSearchAndFilter())
            }

        })

    }


    private fun updatePhamacies(data: List<Pharmacy>?) {

        pharmacies = data
    }

    private var pharmacies: List<Pharmacy>? = LinkedList()


    private fun updateHealthAreas(data: List<HealthArea>?) {
        healthAreas = data
    }


    private var healthAreas: List<HealthArea>? = LinkedList()


    private fun updatePopularProducts(data: List<Product>?) {
        popularProducts = data
        productsAdapter?.updateList(popularProducts)
    }

    private var productsAdapter: ProductAdapter? = null
    private var popularProducts: List<Product>? = LinkedList()
    private fun initPopularProducts() {

        productsAdapter =
            ProductAdapter(this, popularProducts, object : OnRecyclerViewItemClick {
                override fun onClickListener(position: Int) {

                    val intent = Intent(this@ProductSearch, ProductView::class.java)
                    intent.putExtra(Constants().PRODUCT, popularProducts?.get(position))
                    startActivity(intent)

                }

                override fun onLongClickListener(position: Int) {
                    val intent = Intent(this@ProductSearch, ProductView::class.java)
                    intent.putExtra(Constants().PRODUCT, popularProducts?.get(position))
                    startActivity(intent)
                }
            })

        val spanCount = 2
        val layoutManager = GridLayoutManager(this, spanCount)
        recyclerview_popular_products.layoutManager = layoutManager
        recyclerview_popular_products.itemAnimator = DefaultItemAnimator()
        recyclerview_popular_products.adapter = productsAdapter
        productsAdapter!!.notifyDataSetChanged()

    }


    override fun onResume() {
        super.onResume()
        searchProducts()
    }
}
