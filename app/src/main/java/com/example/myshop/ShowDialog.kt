package com.example.myshop

import android.app.Activity
import android.app.AlertDialog
import androidx.fragment.app.Fragment

fun Activity.showMessage(title: String, message:String) {
   AlertDialog.Builder(this)
       .setTitle(title)
       .setMessage(message)
       .create()
       .show()
}

fun Fragment.showMessage(title: String, message:String) {
    AlertDialog.Builder(requireContext())
        .setTitle(title)
        .setMessage(message)
        .create()
        .show()
}