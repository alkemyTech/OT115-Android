package com.alkemy.ongandroid.businesslogic.managers

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

class AnalyticsLogsNewsTestimonialSlideManagerImp(context: Context):
    AnalyticsLogsNewsTestimonialSlideManager {

    private val analytics = FirebaseAnalytics.getInstance(context)

    companion object{
        private const val KEY_EVENT: String = "event_name"
        private const val NEWS_PRESSED_EVENT: String = "last_news_see_more_pressed"
        private const val NEWS_SUCCESS_EVENT: String = "last_news_retrieve_success"
        private const val NEWS_FAILURE_EVENT: String = "last_news_retrieve_error"

        private const val TESTIMONIAL_PRESSED_EVENT: String = "testimonies_see_more_pressed"
        private const val TESTIMONIAL_SUCCESS_EVENT: String = "testimonies_retrieve_success"
        private const val TESTIMONIAL_FAILURE_EVENT: String = "testimonies_retrieve_error"

        private const val SLIDER_SUCCESS_EVENT: String = "slider_retrieve_success"
        private const val SLIDER_FAILURE_EVENT: String = "slider_retrieve_error"
    }


    override fun newsPressedEvent() {
        val bundle = Bundle()
        bundle.putString(KEY_EVENT, NEWS_PRESSED_EVENT)
        analytics.logEvent(NEWS_PRESSED_EVENT,bundle)
    }

    override fun newsSuccessGetEvent() {
        val bundle = Bundle()
        bundle.putString(KEY_EVENT, NEWS_SUCCESS_EVENT)
        analytics.logEvent(NEWS_SUCCESS_EVENT,bundle)
    }

    override fun newsFailureGetEvent() {
        val bundle = Bundle()
        bundle.putString(KEY_EVENT, NEWS_FAILURE_EVENT)
        analytics.logEvent(NEWS_FAILURE_EVENT,bundle)
    }

    override fun testimoniesPressedEvent() {
        val bundle = Bundle()
        bundle.putString(KEY_EVENT, TESTIMONIAL_PRESSED_EVENT)
        analytics.logEvent(TESTIMONIAL_PRESSED_EVENT,bundle)
    }

    override fun testimoniesSuccessEvent() {
        val bundle = Bundle()
        bundle.putString(KEY_EVENT, TESTIMONIAL_SUCCESS_EVENT)
        analytics.logEvent(TESTIMONIAL_SUCCESS_EVENT,bundle)
    }

    override fun testimoniesFailureEvent() {
        val bundle = Bundle()
        bundle.putString(KEY_EVENT, TESTIMONIAL_FAILURE_EVENT)
        analytics.logEvent(TESTIMONIAL_FAILURE_EVENT,bundle)
    }

    override fun sliderSuccessEvent() {
        val bundle = Bundle()
        bundle.putString(KEY_EVENT, SLIDER_SUCCESS_EVENT)
        analytics.logEvent(SLIDER_SUCCESS_EVENT,bundle)
    }

    override fun sliderFailureEvent() {
        val bundle = Bundle()
        bundle.putString(KEY_EVENT, SLIDER_FAILURE_EVENT)
        analytics.logEvent(SLIDER_FAILURE_EVENT,bundle)
    }

}