
# ASWebIconFetcher

ASWebIconFetcher is a lightweight Android library for fetching website icons (favicons) using a custom backend API.  
It guarantees that you always receive a valid favicon URL — either the real icon from the website or a reliable placeholder when the domain cannot be resolved.

The library is simple, fast, dependency‑light, and designed to integrate into any Android project with minimal setup.

---

## ✨ Features

- Fetch favicon for any website URL
- Supports **format filters** using include / exclude rules
- Handles invalid or unreachable domains gracefully
- Always returns a favicon (real or placeholder)
- Simple callback-based API
- No heavy dependencies
- Distributed via JitPack

---

## 📦 Requirements

- Android API 29+
- Java 11+
- Internet permission

---

## 🚀 Installation (JitPack)

### Step 1 — Add JitPack repository

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

### Step 2 — Add dependency

```gradle
dependencies {
    implementation 'com.github.aakashsakhalkar:ASWebIconFetcher:v1.0.2'
}
```

---

## 🧠 Usage

Create an instance:

```java
ASWebIconFetcher fetcher = new ASWebIconFetcher();
```

### 1️⃣ Basic fetch

```java
fetcher.fetch("https://google.com", callback);
```

### 2️⃣ Include only specific formats

```java
fetcher.fetchWithInclude(
        "https://google.com",
        "svg,png",
        callback
);
```

### 3️⃣ Exclude specific formats

```java
fetcher.fetchWithExclude(
        "https://google.com",
        "ico,gif",
        callback
);
```

### 4️⃣ Include and Exclude together

```java
fetcher.fetchWithFilters(
        "https://google.com",
        "png,jpg,webp",
        "svg",
        callback
);
```

---

## 📤 Callback Structure

```java
new ASWebIconFetcher.WebIconCallback() {
    @Override
    public void onSuccess(String iconUrl, String domain, String source) {
        // iconUrl -> favicon URL
        // domain  -> resolved domain
        // source  -> html / manifest / placeholder
    }

    @Override
    public void onError(String errorMessage, String iconUrl, String domain) {
        // errorMessage -> error description
        // iconUrl      -> placeholder icon from API
        // domain       -> parsed domain
    }
};
```

---

## 🔍 API Response Behavior

The backend API always returns structured data.

### ✅ Success Response

```json
{
  "favicon": "https://www.google.com/favicon.ico",
  "domain": "google.com",
  "source": "html"
}
```

### ❌ Error Response

```json
{
  "error": "Could not resolve domain name.",
  "favicon": "https://aakashfaviconapi.netlify.app/favicon.ico",
  "domain": "invalid-domain-123.com",
  "source": "placeholder"
}
```

---

## 🔐 Permissions

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

---

## 🏷 Versioning

Current stable version: **v1.0.2**

Follow semantic versioning for future updates.

---

## 📄 License

MIT License

---

## 👤 Author

Aakash Sakhalkar

