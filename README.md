# Test App

> ⚠️ **Important:**  
> Before running the project, make sure to add your API base URL to the `local.properties` file:
> ```
> BASE_URL=https://your.api.url
> ```

Test App is a sample Android application that demonstrates a simple digital wallet system. It allows
users to create accounts, manage cards, and activate promo codes — all built using modern Android
development tools and clean architecture principles.

## 🧩 Features

- Create a new user
- View user wallet info:
    - Active payment method (card or cash)
    - List of all cards
- Add a promo code
- Add a new card

## 🛠 Tech Stack

- **Jetpack Compose** – for building the UI
- **Dagger Hilt** – for dependency injection
- **Ktor** – for networking
- **DataStore** – for local preferences
- **Clean Architecture** – separation of concerns

## 🚀 Getting Started

# 1. Clone the project

git clone https://github.com/Shera001/WeDrive.git

# 2. Open the project in Android Studio

# 3. Add your BASE_URL to local.properties

echo BASE_URL=https://your.api.url >> local.properties

# 4. Sync Gradle and run the app on a device or emulator

