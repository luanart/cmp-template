This is a Kotlin Multiplatform project targeting Android, iOS.

### Build and Run Android Application
To build and run the development version of the Android app, use the run configuration from the run widget
in your IDE’s toolbar or build it directly from the terminal:
- on macOS/Linux
  ```shell
  ./gradlew :androidApp:assembleDebug
  ```
  
- on Windows
  ```shell
  .\gradlew.bat :androidApp:assembleDebug
  ```

### Build and Run iOS Application
To build and run the development version of the iOS app, use the run configuration from the run widget
in your 's toolbar or open the [/iosApp](./iosApp) directory in Xcode and run it from there.

Config.xcconfig will updated when you run iosApp based on gradle.properties.

Run this for resolve SPM Package when running from android.

  ```shell
  xcodebuild -resolvePackageDependencies -project iosApp/iosApp.xcodeproj
  ```

### Firebase Android Setup
To connect the android app to your Firebase project, you'll need to add the `google-services.json` file.

1.  Go to your Firebase project settings.
2.  In the "Your apps" card, select the Android app.
3.  Download the `google-services.json` file.
4.  Place the file in the following locations:
    *   For the debug build, place it in `androidApp/src/debug/`.
    *   For the release build, place it in `androidApp/src/release/`.

### Firebase iOS Setup
To connect the iOS app to your Firebase project, you'll need to add the `GoogleService-Info.plist` file.

1.  Go to your Firebase project settings.
2.  In the "Your apps" card, select the iOS app.
3.  Download the `GoogleService-Info.plist` file.
4.  Place the file in the `iosApp/iosApp/FirebaseConfig` directory and rename it based on the build configuration:
    *   For the debug build, rename it to `Debug-GoogleService-Info.plist`.
    *   For the release build, rename it to `Release-GoogleService-Info.plist`.
---