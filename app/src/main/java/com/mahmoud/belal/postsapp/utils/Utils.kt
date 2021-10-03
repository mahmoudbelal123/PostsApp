package com.mahmoud.belal.postsapp.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.mahmoud.belal.postsapp.BuildConfig
import com.mahmoud.belal.postsapp.R
import android.content.pm.PackageManager

import android.content.ComponentName




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
    builder.setPositiveButton(R.string.cancel) { dialog, which ->
        dialog.dismiss()
    }
    builder.create()
    builder.show()
}

fun Fragment.showUpdateApplicationAlert(title: String, messageDetails: String) {
    val APP_LINK_ON_GOOGLE_PLAY =
        "play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID

    val builder = AlertDialog.Builder(requireContext())
    builder.setTitle(title)
    builder.setMessage(messageDetails)
    builder.setNegativeButton(R.string.cancel) { dialog, which ->
        dialog.dismiss()
    }
    builder.setPositiveButton(R.string.update) { dialog, which ->
        var intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://" + APP_LINK_ON_GOOGLE_PLAY))
        try {
            startActivity(intent)
        } catch (exception: Exception) {
            intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://" + APP_LINK_ON_GOOGLE_PLAY))
            startActivity(intent)
        }
    }

    builder.create()
    builder.show()
}


fun changeAppIconDynamically(context: Context, isNewIcon: Boolean) {
    val pm = context.applicationContext.packageManager
    if (isNewIcon) {
        pm.setComponentEnabledSetting(
            ComponentName(
                context,
                "com.mahmoud.belal.postsapp.application.MainActivity1"
            ),  //com.example.dummy will be your package
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
            PackageManager.DONT_KILL_APP
        )
        pm.setComponentEnabledSetting(
            ComponentName(
                context,
                "com.mahmoud.belal.postsapp.application.MainActivity"
            ),
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )
    } /*else {
        pm.setComponentEnabledSetting(
            ComponentName(
                context,
                "com.mahmoud.belal.postsapp.application.MainActivity1"
            ),
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )
        pm.setComponentEnabledSetting(
            ComponentName(
                context,
                "com.mahmoud.belal.postsapp.application.MainActivity"
            ),
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
            PackageManager.DONT_KILL_APP
        )
    }*/
}