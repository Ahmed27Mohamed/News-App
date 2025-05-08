# News App - Kotlin Android App

A modern news app built with Kotlin that supports multiple languages (English and Arabic), dark mode, and offline/online functionality. It uses Retrofit for network calls and Room for offline data storage. The app is designed to be responsive and adaptive to different screen sizes, leveraging libraries like SSP and SDP for scalable pixel-perfect UI elements. The architecture follows the MVVM pattern, ensuring separation of concerns, and making the app scalable and maintainable.

## Features

- **Multi-language Support**: Switch between English and Arabic seamlessly.
- **Dark Mode**: Toggle between light and dark mode depending on user preferences.
- **Online & Offline**: View the latest news while online, and access saved news articles when offline.
- **Image Loading**: Glide library used for efficient image loading and caching.
- **Responsive UI**: SSP (Scalable Size Pixel) and SDP (Scalable Density Pixel) are used for consistent layout on various screen sizes and resolutions.
- **MVVM Architecture**: Uses ViewModel, LiveData, and Repository to manage UI-related data in a lifecycle-conscious way.
- **Room Database**: Stores favorite news articles offline, so users can view them even without an internet connection.
- **Coroutines**: For performing asynchronous operations such as network calls and database access without blocking the main thread.

## Technologies Used

- **Kotlin**: The official language for Android development.
- **MVVM (Model-View-ViewModel)**: Architectural pattern used for managing the UI-related data.
- **Room Database**: For offline data persistence.
- **Retrofit**: For making network requests to fetch news from an external API.
- **Glide**: Efficient image loading and caching.
- **Coroutines**: For background thread operations.
- **SSP & SDP**: Libraries to ensure UI is responsive and adapts to different screen sizes and pixel densities.
- **Dark Mode Support**: Native dark mode integration with theme switching.
- **Multi-Language Support**: English and Arabic languages with dynamic switching.

### Prerequisites

1. Android Studio (Latest version)
2. Android device or Emulator
3. API Key from [NewsAPI](https://newsapi.org/) for news fetching.
