<p align="center">
  <img src="app/src/main/ic_launcher-playstore.png" alt="ReadingLight" width="120" />
</p>

<h1 align="center">ReadingLight</h1>

<p align="center">
  Transformez votre téléphone en lampe de lecture douce pour lire la nuit sans vous éblouir.
</p>

<p align="center">
  <a href="https://github.com/lordyhas/reading-light-app/releases/latest"><img src="https://img.shields.io/github/v/release/lordyhas/reading-light-app?label=version" alt="Dernière version" /></a>
  <a href="https://github.com/lordyhas/reading-light-app/actions/workflows/ci.yml"><img src="https://github.com/lordyhas/reading-light-app/actions/workflows/ci.yml/badge.svg" alt="CI" /></a>
  <img src="https://img.shields.io/badge/Android-7.0%2B-3DDC84?logo=android&logoColor=white" alt="Android 7.0+" />
</p>

---

ReadingLight remplit l'écran d'une lumière unie et réglable, pensée pour éclairer un livre papier dans le noir ou servir de veilleuse. Les teintes chaudes limitent la lumière bleue pour préserver votre sommeil.

## Fonctionnalités

- **Teintes recommandées** : Rouge Mélatonine (zéro lumière bleue), Ambre Chaud, Blanc Doux et Sépia.
- **Couleur personnalisée** : choisissez exactement la teinte qui vous convient.
- **Luminosité réglable** de 1 % à 100 %, appliquée à l'appli uniquement, sans toucher aux réglages du système.
- **Minuterie de sommeil** (15, 30 ou 60 min) : la lumière baisse progressivement pendant la dernière minute, puis l'appli se ferme.
- **Plein écran immersif** : touchez l'écran pour afficher ou masquer les commandes, qui se cachent seules après quelques secondes.
- **L'écran reste allumé** tant que l'appli est ouverte.
- **Vos réglages sont mémorisés** d'une session à l'autre.
- Aucune publicité, aucun compte, aucune permission demandée.

## Installation

1. Téléchargez le fichier `ReadingLight-x.y.z.apk` depuis la [dernière release](https://github.com/lordyhas/reading-light-app/releases/latest).
2. Ouvrez-le sur votre téléphone. Android vous demandera peut-être d'autoriser l'installation d'applications depuis cette source.

Nécessite **Android 7.0 (API 24)** ou plus récent.

## Compiler le projet

Prérequis : [Android Studio](https://developer.android.com/studio) récent. Le JDK nécessaire est téléchargé automatiquement par Gradle.

```bash
git clone https://github.com/lordyhas/reading-light-app.git
cd reading-light-app
./gradlew assembleDebug        # APK de debug : app/build/outputs/apk/debug/
./gradlew testDebugUnitTest    # tests unitaires
```

Une build `release` sans keystore configuré produit un APK non signé. Pour signer localement, créez un fichier `keystore.properties` à la racine (il est ignoré par Git) :

```properties
storeFile=/chemin/vers/votre-keystore.jks
storePassword=...
keyAlias=...
keyPassword=...
```

## Stack technique

- Kotlin et [Jetpack Compose](https://developer.android.com/jetpack/compose) (Material 3)
- Architecture MVVM : `ViewModel` + `StateFlow`
- [DataStore Preferences](https://developer.android.com/topic/libraries/architecture/datastore) pour la persistance des réglages

```
app/src/main/java/com/lordyhas/readinglight/
├── MainActivity.kt
├── data/            # Persistance des préférences (DataStore)
├── ui/
│   ├── main/        # Écran principal, état et ViewModel
│   ├── components/  # Panneau de contrôle, préréglages, sélecteur de couleur
│   └── theme/
└── util/            # Luminosité, mode immersif, écran allumé
```

## Releases

Chaque push sur la branche `production` déclenche [le workflow de release](.github/workflows/release.yml). Il lance les tests, construit l'APK et l'AAB signés, puis publie une GitHub Release `v<versionName>`. La version se change dans `app/build.gradle.kts`.

## Contribuer

Les issues et les pull requests sont les bienvenues. Ouvrez vos PR vers la branche `main` : la CI y lance les tests, le lint et une build de debug.

---

<p align="center">
  Développé par <a href="https://github.com/lordyhas">Hassan Kajila</a> · KDynamics Lab
</p>
