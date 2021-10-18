# Neaby Places Project (MVVM)

Introduction
------------

This is an application for finding nearby locations with Foursquare API, that uses MVVM and Repository pattern, Offline-First, Single Source Of Truth Pattern with some principles of Clean Architecture.

In this app I was followed Google recommended [Guide to app architecture](https://developer.android.com/jetpack/docs/guide).

![](/screenshot/mvvm-arch.png)


This application is written in Kotlin language.

Android Jetpack and Architecture Components Used almost everywhere in the app. for example you can see usage of the ViewModel, LiveData,
Lifecycles, Paging, Navigation Component, Room and Data Binding. See a complete list in "Libraries used" section.

This app does network requests via Retrofit, OkHttp and GSON. Loaded data is saved to
SQL based database Room, which serves as single source of truth and support offline mode.
Paging library is used for data pagination online and offline.


Kotlin Coroutines manage background threads with simplified code and reducing needs for callbacks.

Dagger 2 is used for dependency injection.

Coil (Kotlin ImageLoader) is used for image loading and Timber for logging.

Stetho is used to debugging works (like Network calls log, Database content overview,
UI Hierarchy view, etc).

Tests
-----------

This app is not fully TDD but I tried to write tests for the app as much as possible.
There are two Main for testing the project:
* `connectedAndroidTest` - for running Espresso on a connected device
* `test` - for running unit tests

Screenshots
-----------
![](/screenshot/screen1.png)
![](/screenshot/screen2.png)

Tools and patterns Used
--------------


* [Core components][0]
  * [AppCompat][1] 
  * [Android KTX][2]
  * [Test][4] 
* [Architecture][10]
  * [Data Binding][11] 
  * [Lifecycles][12]
  * [LiveData][13]
  * [Navigation][14]
  * [Room][16]
  * [ViewModel][17]
  * [Paging][19]
* [UI][30]
  * [Fragment][34]
  * [Layout][35] 
  * [Material][36]
  * [Kotlin Coroutines][91] 
  * [Dagger 2][92] 
  * [Retrofit 2][93] 
  * [OkHttp 3][94] 
  * [GSON][95]
  * [Coil][90] 
  * [Timber][96] 
  * [Stetho][97] 

[0]: https://developer.android.com/jetpack/components
[1]: https://developer.android.com/topic/libraries/support-library/packages#v7-appcompat
[2]: https://developer.android.com/kotlin/ktx
[4]: https://developer.android.com/training/testing/
[10]: https://developer.android.com/jetpack/arch/
[11]: https://developer.android.com/topic/libraries/data-binding/
[12]: https://developer.android.com/topic/libraries/architecture/lifecycle
[13]: https://developer.android.com/topic/libraries/architecture/livedata
[14]: https://developer.android.com/topic/libraries/architecture/navigation/
[16]: https://developer.android.com/topic/libraries/architecture/room
[17]: https://developer.android.com/topic/libraries/architecture/viewmodel
[19]: https://developer.android.com/topic/libraries/architecture/paging
[30]: https://developer.android.com/guide/topics/ui
[31]: https://developer.android.com/training/animation/
[34]: https://developer.android.com/guide/components/fragments
[35]: https://developer.android.com/guide/topics/ui/declaring-layout
[36]: https://material.io/develop/android/docs/getting-started/
[90]: https://github.com/coil-kt/coil
[91]: https://kotlinlang.org/docs/reference/coroutines-overview.html
[92]: https://dagger.dev/users-guide
[93]: https://square.github.io/retrofit/
[94]: https://square.github.io/okhttp/
[95]: https://github.com/google/gson
[96]: https://github.com/JakeWharton/timber
[97]: http://facebook.github.io/stetho/
