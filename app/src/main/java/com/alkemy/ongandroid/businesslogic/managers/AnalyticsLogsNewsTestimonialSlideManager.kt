package com.alkemy.ongandroid.businesslogic.managers

interface AnalyticsLogsNewsTestimonialSlideManager {

    fun newsPressedEvent()

    fun newsSuccessGetEvent()

    fun newsFailureGetEvent()

    fun testimoniesPressedEvent()

    fun testimoniesSuccessEvent()

    fun testimoniesFailureEvent()

    fun sliderSuccessEvent()

    fun sliderFailureEvent()
}