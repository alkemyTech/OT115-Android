package com.alkemy.ongandroid.businesslogic.usescases

import com.google.android.gms.auth.api.signin.GoogleSignInOptions

interface GetGoogleConfigurationUseCase {

    operator fun invoke(): GoogleSignInOptions

}