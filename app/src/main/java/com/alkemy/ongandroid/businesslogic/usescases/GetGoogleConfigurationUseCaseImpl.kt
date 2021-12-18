package com.alkemy.ongandroid.businesslogic.usescases

import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class GetGoogleConfigurationUseCaseImpl: GetGoogleConfigurationUseCase {
    override fun invoke(): GoogleSignInOptions {
        return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
    }
}