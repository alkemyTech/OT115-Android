package com.alkemy.ongandroid.core

import android.content.Context
import android.widget.Toast

fun Any.toast(context: Context, message:String, isLong: Boolean = false){
    if (isLong){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }else{
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}