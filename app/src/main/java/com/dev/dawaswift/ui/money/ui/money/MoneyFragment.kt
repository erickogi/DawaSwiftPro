package com.dev.dawaswift.ui.money.ui.money

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.dev.dawaswift.R

class MoneyFragment : Fragment() {

    companion object {
        fun newInstance() = MoneyFragment()
    }

    private lateinit var viewModel: MoneyViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.money_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MoneyViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
