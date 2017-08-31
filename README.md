# Udacity Project: Popular Movies (part 2)
Simple app that shows a grid of the most popular movies and arranges them
either by popularity or ratings.

This app needs an API key from The Movie DB website to work.
You can request one from here: https://www.themoviedb.org
upon completing registration.

For the app to work, append this line in your ~/.gradle/gradle.properties file:
  `MyMoviedbApiKey = "put your api key here"`

If you are accessing an older commit, you might need to add the MyMoviedbApiKey
variable in the app/build.gradle file, like that:

`buildTypes.each {
  it.buildConfigField "String", "MOVIEDB_API_KEY", MyMoviedbApiKey
}`

or in alternative, put directly the api key in there:

`buildTypes.each {
  it.buildConfigField "String", "MOVIEDB_API_KEY", "\"Your api key goes here\""
}`
