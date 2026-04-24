package com.moonwater.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

object APKDownloadHelper {
    /**
     * Opens the browser to download the MoonWater APK.
     * Replace the URL with a real hosted APK file link.
     * Example hosting options: Firebase Hosting, GitHub Releases, or direct server.
     */
    private const val APK_URL = "https://example.com/moonwater.apk"

    fun download(context: Context) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(APK_URL))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}
