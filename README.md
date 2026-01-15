# ASWebIconFetcher

ASWebIconFetcher is a lightweight Android library for fetching website icons (favicons) using a custom backend API.  
It handles valid domains, invalid domains, and fallback placeholders in a consistent way.

The library is designed to be simple, fast, and easy to integrate into any Android project.

---

## Features

- Fetch favicon for any website URL
- Handles invalid or unreachable domains gracefully
- Uses a custom API that always returns a favicon (real or placeholder)
- Simple callback-based API
- No heavy dependencies
- JitPack ready

---

## Requirements

- Android API 29+
- Java 11
- Internet permission

---

## Installation

ASWebIconFetcher is distributed via **JitPack**.

### Step 1: Add JitPack repository

#### Groovy (`settings.gradle`)

```gradle
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```

#### Kotlin DSL (`settings.gradle.kts`)

```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```

---

### Step 2: Add the dependency

#### Groovy

```gradle
dependencies {
    implementation 'com.github.aakashsakhalkar:ASWebIconFetcher:v1.0.0'
}
```

#### Kotlin DSL

```kotlin
dependencies {
    implementation("com.github.aakashsakhalkar:ASWebIconFetcher:v1.0.0")
}
```

---

## Usage

```java
ASWebIconFetcher fetcher = new ASWebIconFetcher();

fetcher.fetch("https://google.com", new ASWebIconFetcher.WebIconCallback() {

    @Override
    public void onSuccess(String iconUrl, String domain, String source) {
        // iconUrl -> favicon URL
        // domain  -> resolved domain
        // source  -> real / placeholder
    }

    @Override
    public void onError(String errorMessage, String iconUrl, String domain) {
        // errorMessage -> error description
        // iconUrl      -> placeholder icon from API
        // domain       -> parsed domain (if available)
    }
});
```

---

## API Behavior

The backend API always returns a favicon URL.

### Success response
```json
{
  "favicon": "https://example.com/favicon.ico",
  "domain": "example.com",
  "source": "real"
}
```

### Error response
```json
{
  "error": "Could not resolve domain name.",
  "favicon": "https://aakashfaviconapi.netlify.app/favicon.ico",
  "domain": "invalid-domain",
  "source": "placeholder"
}
```

---

## Permissions

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

---

## Versioning

Current version: **v1.0.0**

---

## License

MIT License

---

## Author

**Aakash Sakhalkar**
