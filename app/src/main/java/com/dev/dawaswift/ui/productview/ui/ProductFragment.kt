package com.dev.dawaswift.ui.productview.ui

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import androidx.fragment.app.Fragment
import com.dev.common.data.Constants
import com.dev.dawaswift.R
import com.dev.dawaswift.models.Product.Product
import com.dev.common.slider.Animations.DescriptionAnimation
import com.dev.common.slider.SliderLayout
import com.dev.common.slider.SliderTypes.BaseSliderView
import com.dev.common.slider.SliderTypes.TextSliderView
import kotlinx.android.synthetic.main.product_view_fragment.*

class ProductFragment : Fragment(), BaseSliderView.OnSliderClickListener, RatingBar.OnRatingBarChangeListener, View.OnClickListener {
    override fun onClick(p0: View?) {
    }

    override fun onRatingChanged(p0: RatingBar?, p1: Float, p2: Boolean) {
    }

    override fun onSliderClick(slider: BaseSliderView?) {

    }

    companion object {
        fun newInstance() = ProductFragment()
    }

    private lateinit var product: Product
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.product_view_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        product=arguments?.getSerializable(Constants().PRODUCT) as Product
        setSliders(product.images)
        setUpProductUi(product)

    }


    private fun setSliders(sliders: List<String>?) {

        if (sliders != null) {
            for (slider in sliders) {
                val textSliderView = TextSliderView(context)
                textSliderView
                    .description("")
                    .image( slider)
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this)
                textSliderView.bundle(Bundle())
                textSliderView.bundle
                    .putString("extra", "")
                sliderLayout.addSlider(textSliderView)
            }
        }



        sliderLayout.isClickable = false
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion)
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom)
        sliderLayout.setCustomAnimation(DescriptionAnimation())
        sliderLayout.setDuration(3000)
    }
    private fun setUpProductUi(data: Product?) {
        if (data != null) {

            title.text = data.name
            ratingtext.text = data.rating
            cutprice.text = "Ksh" + data.price
            price.text = "Ksh" + data.discountedPrice
            description.text = "Ksh" + data.description
            cutprice.paintFlags = cutprice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG


            if (data.isFavorite != null) {
                if (data.isFavorite!!) {
                    fav2.visibility = View.VISIBLE
                    fav1.visibility = View.GONE
                } else {
                    fav1.visibility = View.VISIBLE
                    fav2.visibility = View.GONE
                }
                ratingbar.rating = data.rating?.toFloat()!!
            }


            ratingbar.onRatingBarChangeListener = this
            fav1.setOnClickListener(this)
            fav2.setOnClickListener(this)
        }
    }



}
