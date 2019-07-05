package com.dev.dawaswift.ui.productview

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.dev.badge.BadgeView
import com.dev.common.data.Constants
import com.dev.common.models.custom.Status
import com.dev.common.utils.viewUtils.ViewUtils
import com.dev.dawaswift.R
import com.dev.dawaswift.adapters.product.ViewPagerAdapter
import com.dev.dawaswift.models.Product.Product
import com.dev.dawaswift.models.cart.AddItem
import com.dev.dawaswift.ui.CommonMainViewModel
import com.dev.dawaswift.ui.cart.CartActivity
import com.dev.dawaswift.ui.productview.ui.DetilsFragment
import com.dev.dawaswift.ui.productview.ui.ProductFragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.product_search_activity.container
import kotlinx.android.synthetic.main.product_view_activity.*
import kotlinx.android.synthetic.main.toolbar_product_view.*

class ProductView : AppCompatActivity() {

    lateinit var qBadgeView: BadgeView
    private lateinit var viewModel: CommonMainViewModel


    var productId: Int? = 0
    var product: Product? = null

    val menuFragment = MenuFragment()
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.product_view_activity)
        linear_back.setOnClickListener {
            onBackPressed()
        }
        viewModel = ViewModelProviders.of(this).get(CommonMainViewModel::class.java)

        product= intent.getSerializableExtra(Constants().PRODUCT) as Product


        com.dev.common.utils.viewUtils.ViewUtils().makeFullScreen(this)
       qBadgeView = BadgeView(this)


        setupViewPager(viewpager)
        tabs.tabGravity = TabLayout.GRAVITY_FILL
        tabs.setupWithViewPager(viewpager)
        setupTabIcons()




        btnAddCart.setOnClickListener {

            menuFragment.arguments= Bundle().apply {
            putSerializable(Constants().PRODUCT, product)
        }
            menuFragment.show(supportFragmentManager, menuFragment.tag)

        }
        btnBuy.setOnClickListener {
            menuFragment.arguments= Bundle().apply {
                putSerializable(Constants().PRODUCT, product)
            }
            menuFragment.show(supportFragmentManager, menuFragment.tag)
        }

        viewModel.viewCart()
        viewModel.observeCart().observe(this, Observer {

            ViewUtils.setStatus(
                this,
                container,
                it.status,
                it.message,
                false,
                ViewUtils.ErrorViewTypes.NON,
                it.exception
            )
            if (it.status == Status.SUCCESS) {


                setCartCount(it.data?.data?.itemsCount)

            }
        })


        cart.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
            finish()
        }

    }

    private fun setCartCount(count: Int?) {
        if (count == null) {
            qBadgeView.bindTarget(cart).badgeText = ""
        }
        qBadgeView.bindTarget(cart).badgeText = "" + count


    }

    fun dismissSheet() {
        menuFragment.dismiss()
    }

    fun dismissSheet(addItem: AddItem) {
        menuFragment.dismiss()
        viewModel.addCart(addItem)
    }

    fun snack(text: String) {
        Snackbar.make(btnAddCart, "Item added to cart successfully", Snackbar.LENGTH_LONG).show()
    }

    fun getProductId(): Int {
        return this.productId!!
    }


    private fun setupViewPager(viewPager: ViewPager) {


        val fragment = ProductFragment()

        val fragment1 = DetilsFragment()


        fragment.arguments= Bundle().apply {
            putSerializable(Constants().PRODUCT, product)
        }
        fragment1.arguments= Bundle().apply {
            putSerializable(Constants().PRODUCT, product)
        }
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(fragment, "Product")
        adapter.addFragment(fragment1, "Details")


        viewPager.offscreenPageLimit = 1
        viewPager.adapter = adapter
    }

    private fun setupTabIcons() {

        tabs.getTabAt(0)?.text = "Product"
        tabs.getTabAt(1)?.text = "Details"


    }


}
