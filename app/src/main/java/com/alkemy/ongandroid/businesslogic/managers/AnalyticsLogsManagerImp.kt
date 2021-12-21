package com.alkemy.ongandroid.businesslogic.managers

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

class AnalyticsLogsManagerImp(context: Context) : AnalyticsLogsManager {

    private val analytics = FirebaseAnalytics.getInstance(context)

    companion object{
        private const val KEY_EVENT: String = "event_name"
        private const val LOG_IN_PRESSED_EVENT: String = "log_in_pressed"
        private const val SIGN_UP_PRESSED_EVENT: String = "sign_up_pressed"
        private const val SIGN_UP_SUCCESS_EVENT: String = "sign_up_succeeded"
        private const val SIGN_UP_FAILURE_EVENT: String = "sign_up_failure"
        private const val GMAIL_PRESSED_EVENT: String = "gmail_pressed"
        private const val FACEBOOK_PRESSED_EVENT: String = "facebook_pressed"
        private const val LOG_IN_SUCCESS_EVENT: String = "log_in_success"
        private const val LOG_IN_ERROR_EVENT: String = "log_in_error"
    }

    override fun registerLogInPressedEvent() {
        val bundle = Bundle()
        bundle.putString(KEY_EVENT, LOG_IN_PRESSED_EVENT)
        analytics.logEvent(LOG_IN_PRESSED_EVENT,bundle)
    }

    override fun registerSignUpPressedEvent() {
        val bundle = Bundle()
        bundle.putString(KEY_EVENT, SIGN_UP_PRESSED_EVENT)
        analytics.logEvent(SIGN_UP_PRESSED_EVENT,bundle)
    }

    override fun registerSignUpSuccess() {
        val bundle = Bundle()
        bundle.putString(KEY_EVENT, SIGN_UP_SUCCESS_EVENT)
        analytics.logEvent(SIGN_UP_SUCCESS_EVENT,bundle)
    }

    override fun registerSignUpFailure() {
        val bundle = Bundle()
        bundle.putString(KEY_EVENT, SIGN_UP_FAILURE_EVENT)
        analytics.logEvent(SIGN_UP_FAILURE_EVENT,bundle)
    }

    override fun registerGmailPressedEvent() {
        val bundle = Bundle()
        bundle.putString(KEY_EVENT, GMAIL_PRESSED_EVENT)
        analytics.logEvent(GMAIL_PRESSED_EVENT,bundle)
    }

    override fun registerFacebookPressedEvent() {
        val bundle = Bundle()
        bundle.putString(KEY_EVENT, FACEBOOK_PRESSED_EVENT)
        analytics.logEvent(FACEBOOK_PRESSED_EVENT,bundle)
    }

    override fun registerLogInSuccessEvent() {
        val bundle = Bundle()
        bundle.putString(KEY_EVENT, LOG_IN_SUCCESS_EVENT)
        analytics.logEvent(LOG_IN_SUCCESS_EVENT,bundle)
    }

    override fun registerLogInErrorEvent() {
        val bundle = Bundle()
        bundle.putString(KEY_EVENT, LOG_IN_ERROR_EVENT)
        analytics.logEvent(LOG_IN_ERROR_EVENT,bundle)
    }
}