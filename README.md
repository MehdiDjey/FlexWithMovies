# FlexWithMovies

Android App using The Movie DB API

# Description
Shows a list of the latest Movies sorted by popularity, which could be drilled down to view the Detail.

# Import the project in AndroidStudio, and add API Key

- Clone or fork the repository
- In Android Studio, go to File -> New -> Import project
- Fellow the dialog wizard to choose the folder where you cloned the project and click on open.
- Androidstudio imports the projects and builds it for you.
- Add TheMovieDb API Key inside '../main/cpp/native-lib.cpp' file. **[IMPORTANT]**
- Enjoy it :)


# Requirements
Minimum API:  23

# Architecture
- MVVM
- Repository Pattern
- One Activity Pattern

# Libraries Used
- AndroidX
- Coil
- Retrofit
- Kotlinx Serialization
- LiveData
- ViewModel
  _ ViewBiding
- Timber
- Sandwich

# TODO :
- [ ] Movies search
- [ ] Movies filter by release date, note and most popular ...etc
- [ ] Split views by categories ( Most view, Newest... etc )
- [ ] Improve views design  ( using compose ??? why not :D )
- [ ] Migrate liveData to Coroutines Flow
- [ ] Migrate Koin to hilt ??
- [ ] Improve unit tests