BookCatalog
BookCatalog is an Android application built with Kotlin that allows users to search, browse, and manage their favorite books. 
The app fetches book data from the internet, using the Google Books API, and saves it to a Room database for offline access. 
The app follows the MVVM architecture and includes features like data persistence, a favorites list, and navigation between different sections of the app.

Features

Search for Books: Users can search for books using a search bar, with data fetched from the Google Books API.
View Book Details: Detailed information about each book, including title, author, and description, is displayed.
Favorite Books: Users can add books to their favorites list for quick access later.
Offline Access: Favorite books are stored locally in a Room database, allowing users to view them even without an internet connection.
Navigation: Intuitive navigation using Android Jetpack Navigation Component.
Dynamic UI: The app handles empty states and errors, showing a message when there are no favorite books or in case of a data fetching error.

Technologies Used

Kotlin: The main language used to build the app.
Room: Database library for local data storage and offline access.
Retrofit: For network calls to the Google Books API.
Coroutines: For managing background tasks and asynchronous operations.
LiveData & ViewModel: For observing changes in data and managing UI-related data in a lifecycle-conscious way.
Glide: For loading book images efficiently.
Navigation Component: For managing in-app navigation between different screens.
KSP: Kotlin Symbol Processing used for Room database integration.
Secrets Gradle Plugin: Used to securely manage API keys.
