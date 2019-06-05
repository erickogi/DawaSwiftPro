package com.dev.dawaswift.ui.productsearch.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dev.dawaswift.R

class ProductSearchFragment : Fragment() {

    companion object {
        fun newInstance() = ProductSearchFragment()
    }

    private lateinit var viewModel: ProductSearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.product_search_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ProductSearchViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
