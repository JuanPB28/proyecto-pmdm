package edu.juanpascual.mylittleshoppinglist.utils

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.launchIntent(intent: Intent, errorMessage: String) {
    try {
        startActivity(intent)
    } catch (e: Exception) {
        Log.e("LaunchIntent", "Error launching intent: ${e.message}")
        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
    }
}

fun Fragment.launchMapIntent(address: String, errorMessage: String = "Google Maps not installed.") {
    val gmmIntentUri = Uri.parse("geo:0,0?q=${Uri.encode(address)}")
    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
    launchIntent(mapIntent, errorMessage)
}

fun Fragment.launchDialIntent(phoneNumber: String, errorMessage: String = "Calling app not found.") {
    val dialIntent = Intent(Intent.ACTION_DIAL).apply {
        data = Uri.parse("tel:$phoneNumber")
    }
    launchIntent(dialIntent, errorMessage)
}

fun Fragment.launchWebIntent(websiteUrl: String, errorMessage: String = "Web browser not available.") {
    // Validar y asegurar que la URL tiene "http://" o "https://"
    val validatedUrl = if (!websiteUrl.startsWith("http://") && !websiteUrl.startsWith("https://")) {
        "http://$websiteUrl"
    } else {
        websiteUrl
    }
    val webPage = Uri.parse(validatedUrl)
    val webIntent = Intent(Intent.ACTION_VIEW, webPage)
    launchIntent(webIntent, errorMessage)
}