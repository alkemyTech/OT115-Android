package com.alkemy.ongandroid.businesslogic.managers

interface AnalyticsLogsManager {

    fun registerLogInPressedEvent()

    fun registerSignUpPressedEvent()

    fun registerGmailPressedEvent()

    fun registerFacebookPressedEvent()

    fun registerLogInSuccessEvent()

    fun registerLogInErrorEvent()
}