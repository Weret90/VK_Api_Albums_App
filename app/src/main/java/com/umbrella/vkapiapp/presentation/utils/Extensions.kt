package com.umbrella.vkapiapp.presentation.utils

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

fun getColumnCount(activity: Activity?, minColumnCount: Int): Int {
    val displayMetrics = DisplayMetrics()
    activity?.windowManager?.defaultDisplay?.getRealMetrics(displayMetrics)
    val width = displayMetrics.widthPixels / displayMetrics.density
    val columnCount = (width / 185).toInt()
    return if (columnCount > minColumnCount) {
        columnCount
    } else {
        minColumnCount
    }
}

fun View.show() {
    if (visibility != View.VISIBLE) {
        visibility = View.VISIBLE
    }
}

fun View.hide() {
    if (visibility != View.GONE) {
        visibility = View.GONE
    }
}

fun Context?.showToast(text: String?) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}

fun unixTimeConverter(unixTime: Int): String {
    val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    val date = Date(unixTime.toLong() * 1000)
    return sdf.format(date)
}

fun View.showSnackBar(text: String?, actionText: String, action: (View) -> Unit) {
    Snackbar.make(this, text.toString(), Snackbar.LENGTH_INDEFINITE)
        .setAction(actionText, action).show()
}