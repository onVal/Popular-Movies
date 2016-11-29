# Udacity Project: Popular Movies (part 1)

This app needs an API key from The Movie DB website to work.
You can request one from here: https://www.themoviedb.org
upon completing registration.

For the app to work, place your API key in the build.gradle (module: app) file like this:

buildTypes.each {
  it.buildConfigField "String", "MOVIEDB_API_KEY", *"\"your_api_key\""*
}
