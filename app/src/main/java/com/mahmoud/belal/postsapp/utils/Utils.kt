package com.mahmoud.belal.postsapp.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

inline fun <T : View> T.showIf(condition: (T) -> Boolean) {
    if (condition(this)) {
        show()
    } else {
        hide()
    }
}

inline fun <T : View> T.hideIf(condition: (T) -> Boolean) {
    if (condition(this)) {
        hide()
    } else {
        show()
    }
}

fun Context.showToast(message: String, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, message, duration).show()
}

fun Fragment.showToast(message: String, duration: Int = Toast.LENGTH_LONG) {
    requireContext().showToast(message, duration)
}

fun Fragment.showAlert(title: String, messageDetails: String) {
    val builder = AlertDialog.Builder(requireContext())
    builder.setTitle(title)
    builder.setMessage(messageDetails)
    builder.setPositiveButton(android.R.string.cancel) { dialog, which ->
        dialog.dismiss()
    }
    builder.create()
    builder.show()
}