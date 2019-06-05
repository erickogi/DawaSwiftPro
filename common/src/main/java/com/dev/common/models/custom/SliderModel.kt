package com.dev.common.models.custom

class SliderModel {

    var sliderId: Int? = 0
    var sliderImage: Int? = 0
    var sliderText: String? = null

    constructor(sliderId: Int?, sliderImage: Int?, sliderText: String?) {
        this.sliderId = sliderId
        this.sliderImage = sliderImage
        this.sliderText = sliderText
    }
}