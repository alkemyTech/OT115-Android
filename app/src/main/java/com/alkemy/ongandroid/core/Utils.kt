package com.alkemy.ongandroid.core

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Activity.toast(context: Context, message:String, isLong: Boolean = false){
    if (isLong){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }else{
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
fun Fragment.toast(context: Context, message:String, isLong: Boolean = false){
    if (isLong){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }else{
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}