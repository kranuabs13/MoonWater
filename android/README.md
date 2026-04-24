# MoonWater Android Build Instructions

This project is a complete Jetpack Compose Android application.

## How to Build the APK

1. **Install Android Studio**: Ensure you have the latest version of Android Studio installed.
2. **Open Project**: Open the `android/` directory in Android Studio.
3. **Sync Gradle**: Click "Sync Project with Gradle Files" when prompted.
4. **Build APK**:
   - Go to `Build` > `Build Bundle(s) / APK(s)` > `Build APK(s)`.
   - Alternatively, run from terminal:
     ```bash
     cd android
     ./gradlew assembleDebug
     ```

## APK Output Path
The generated APK will be located at:
`android/app/build/outputs/apk/debug/app-debug.apk`

## How to host your APK
To make the "Download APK" button work with your own file:
1. Upload `app-debug.apk` to a hosting provider (GitHub Releases, Firebase Hosting, your own server).
2. Copy the direct download link.
3. Replace the `APK_URL` constant in `android/app/src/main/java/com/moonwater/utils/APKDownloadHelper.kt`.

## Main Technologies
- **Kotlin**: Core language.
- **Jetpack Compose**: Modern declarative UI.
- **DataStore**: Modern alternative to SharedPreferences for local storage.
- **WorkManager**: Reliable periodic background tasks for reminders.
- **Material 3**: Latest design system from Google.
