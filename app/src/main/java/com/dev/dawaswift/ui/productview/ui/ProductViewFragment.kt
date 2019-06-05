package com.dev.dawaswift.ui.productview.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dev.dawaswift.R

class ProductViewFragment : Fragment() {

    companion object {
        fun newInstance() = ProductViewFragment()
    }

    private lateinit var viewModel: ProductViewViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.product_view_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ProductViewViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
