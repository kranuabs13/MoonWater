package com.moonwater.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

object APKDownloadHelper {
    /**
     * Opens the browser to download the MoonWater APK.
     * Replace the URL with your real hosted APK file link.
     */
    private const val APK_URL = "https://ais-dev-zu4i7k254pobblwho3ustu-31510190271.europe-west1.run.app/moonwater.apk"

    fun download(context: Context) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(APK_URL))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}
