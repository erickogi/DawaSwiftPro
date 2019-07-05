package com.dev.dawaswift.ui.category.ui.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.common.data.Constants
import com.dev.common.models.custom.SearchModel
import com.dev.common.utils.CommonUtils
import com.dev.common.utils.OnRecyclerViewItemClick
import com.dev.common.utils.SearchDialogListener
import com.dev.dawaswift.R
import com.dev.dawaswift.adapters.category.SubCategoryAdapter
import com.dev.dawaswift.models.category.Category
import com.dev.dawaswift.models.category.SubCategory
import com.dev.dawaswift.models.category.SubCategoryItem
import com.dev.dawaswift.ui.category.Categories
import kotlinx.android.synthetic.main.sub_categories_fragment.*
import java.util.*

class SubCategoriesFragment : Fragment() {

    companion object {
        fun newInstance() = SubCategoriesFragment()
    }

    private lateinit var viewModel: CategoriesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.sub_categories_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CategoriesViewModel::class.java)
        initCategories()


        var categories=arguments?.getSerializable(Constants().CATEGORY) as Category

        var title=categories.name
        pagetitle.text= "Search By Sub-Categories [ $title ]"

        updateCategories(categories.data)

    }
    private fun updateCategories(data: List<SubCategory>?) {

        subcategories=data
        subCategoryAdapter?.updateList(subcategories)
    }

    private var subcategories: List<SubCategory>?= LinkedList()
    private var subCategoryAdapter: SubCategoryAdapter? = null
    private fun initCategories() {

        subCategoryAdapter = context?.let {
            SubCategoryAdapter(Constants().LINEAR,it, subcategories, object : OnRecyclerViewItemClick {
                override fun onClickListener(position: Int) {

                    val <T> searchModels = ArrayList<SearchModel<SubCategoryItem>>()

                    val  subcategoriesItems=subcategories?.get(position)?.data


                    for (subcategories in subcategoriesItems as List) {
                        searchModels.add(SearchModel(title = subcategories.name!!,id = ""+subcategories.id!!,serices = subcategories))
                    }
                    CommonUtils().searchDialog(context!!,"Search...","Search ites",
                        searchModels,true,object :
                        SearchDialogListener<SubCategoryItem> {
                        override fun onResults(
                            item: SearchModel<SubCategoryItem>?,
                            position: Int
                        ) {

                            val search = viewModel.fetchSearch()



                            search.subCategoryId = null
                            search.subcategoryName = null

                            search.categoryId = null
                            search.categoryName = null


                            search.subCategoryItemId = item?.getServices()?.id
                            search.subcategoryItemName = item?.getServices()?.name

                            viewModel.saveSearch(search)
                            (activity as Categories).onselection()


                        }
                    })



                }

                override fun onLongClickListener(position: Int) {


                    val search = viewModel.fetchSearch()


                    search.categoryId = null
                    search.categoryName = null

                    search.subCategoryItemId = null
                    search.subcategoryItemName = null


                    search.subCategoryId = subcategories!![position].id
                    search.subcategoryName = subcategories!![position].name

                    viewModel.saveSearch(search)
                    (activity as Categories).onselection()

                }
            })
        }!!

        recyclerview_sub_categories.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerview_sub_categories.itemAnimator = DefaultItemAnimator()
        recyclerview_sub_categories.adapter = subCategoryAdapter
        subCategoryAdapter!!.notifyDataSetChanged()

    }


}
