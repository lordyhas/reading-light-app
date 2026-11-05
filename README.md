<p align="center">
  <img src="app/src/main/ic_launcher-playstore.png" alt="ReadingLight" width="120" />
</p>

<h1 align="center">ReadingLight</h1>

<p align="center">
  Turn your phone into a soft reading lamp for reading at night without the glare.
</p>

<p align="center">
  <a href="https://github.com/lordyhas/reading-light-app/releases/latest"><img src="https://img.shields.io/github/v/release/lordyhas/reading-light-app?label=version" alt="Latest version" /></a>
  <a href="https://github.com/lordyhas/reading-light-app/actions/workflows/ci.yml"><img src="https://github.com/lordyhas/reading-light-app/actions/workflows/ci.yml/badge.svg" alt="CI" /></a>
  <img src="https://img.shields.io/badge/Android-7.0%2B-3DDC84?logo=android&logoColor=white" alt="Android 7.0+" />
  <a href="LICENSE"><img src="https://img.shields.io/badge/license-MIT-blue" alt="MIT License" /></a>
</p>

---

ReadingLight fills the screen with a solid, adjustable light, made for lighting up a paper book in the dark or serving as a night light. Warm tints cut down on blue light to protect your sleep.

## Features

- **Recommended tints**: Melatonin Red (zero blue light), Warm Amber, Soft White and Sepia.
- **Custom color**: pick exactly the shade that suits you.
- **Adjustable brightness** from 1% to 100%, applied to the app only, without touching your system settings.
- **Sleep timer** (15, 30 or 60 min): the light fades out gradually over the last minute, then the app closes.
- **Immersive full screen**: tap the screen to show or hide the controls, which also hide on their own after a few seconds.
- **The screen stays on** while the app is open.
- **Your settings are remembered** between sessions.
- **English and French**, following your device language. On Android 13+ you can also choose the app language in system settings.
- No ads, no account, no permissions.

## Installation

1. Download `ReadingLight-x.y.z.apk` from the [latest release](https://github.com/lordyhas/reading-light-app/releases/latest).
2. Open it on your phone. Android may ask you to allow installing apps from this source.

Requires **Android 7.0 (API 24)** or newer.

## Building

Prerequisite: a recent [Android Studio](https://developer.android.com/studio). Gradle downloads the required JDK automatically.

```bash
git clone https://github.com/lordyhas/reading-light-app.git
cd reading-light-app
./gradlew assembleDebug        # debug APK: app/build/outputs/apk/debug/
./gradlew testDebugUnitTest    # unit tests
```

A `release` build without a configured keystore produces an unsigned APK. To sign locally, create a `keystore.properties` file at the project root (it is ignored by Git):

```properties
storeFile=/path/to/your-keystore.jks
storePassword=...
keyAlias=...
keyPassword=...
```

## Tech stack

- Kotlin and [Jetpack Compose](https://developer.android.com/jetpack/compose) (Material 3)
- MVVM architecture: `ViewModel` + `StateFlow`
- [DataStore Preferences](https://developer.android.com/topic/libraries/architecture/datastore) to persist settings

```
app/src/main/java/com/lordyhas/readinglight/
├── MainActivity.kt
├── data/            # Preferences persistence (DataStore)
├── ui/
│   ├── main/        # Main screen, UI state and ViewModel
│   ├── components/  # Control panel, presets, color picker
│   └── theme/
└── util/            # Brightness, immersive mode, keep screen on
```

## Translations

UI strings live in `app/src/main/res/values/strings.xml` (English, default) and `app/src/main/res/values-fr/strings.xml` (French). To add a language, create a `values-<code>/strings.xml` with the translated strings. The language is picked up automatically.

## Releases

Every push to the `production` branch triggers [the release workflow](.github/workflows/release.yml). It runs the tests, builds the signed APK and AAB, and publishes a `v<versionName>` GitHub Release. The version is set in `app/build.gradle.kts`.

## Contributing

Issues and pull requests are welcome. Open PRs against the `main` branch: CI runs the tests, lint and a debug build there.

## License

ReadingLight is released under the [MIT License](LICENSE).

---

<p align="center">
  Built by <a href="https://github.com/lordyhas">Hassan Kajila</a> · KDynamics Lab
</p>
