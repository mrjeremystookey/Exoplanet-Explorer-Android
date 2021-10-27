# Exoplanet-Explorer


#Overview 

Android app written in Kotlin and attempting to follow Clean Architecture principles with MVVM as the design pattern handling the updating of the UI. The UI is written predominatly in Jetpack Compose, the new native declarative UI toolkit for Android. Navigation is handled via the Navigation Graph and uses a BottomNavigation bar to switch between fragments. 

Exoplanet Explorer is used for displaying, searching, and sorting a list of the discovered exoplanets - includes tools for displaying distributions of various planetary attributes. This list is updated periodically as new planets are added to the Caltech database. 


#Libraries Used
Moshi - Parsing of JSON from API
Volley - HTTP calls to Caltech API 
Room - Local cache and data persistence 
Hilt / Dagger2 - Dependency Injection 
Crashlytics - Crash Reporting in Firebase
Firebase Analytics - used to log events inside app
MPCharts - data visulation and plotting 
WorkManager - background API calls and keeping local data up to date. 
