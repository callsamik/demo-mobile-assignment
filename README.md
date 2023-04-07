# Demo Mobile Assignment
This is a README file with the details of the implementation of the assignment for Android native platform.

## Calling the configuration API
To get the images from the API we need the image URLs, to form the image URLs we need to call the configuration API first. Because the configuration API response sends the image base URLs and the available image resolution options.

## Getting the movies "now playing" list
Used the webservice to fetch the "now playing" list for the movies. For the image URLs used the configuration API response to get the base API for the images.

## Getting the "popular movies" list
Used the webservice to fetch the "popular movies" list for the movies. For the image URLs used the configuration API response to get the base API for the images. Also implementated pagination support for this API using [Android Paging Library v3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview). Also fetching the movie details for every movie using the specified web API and movie id as parameter to the API.

## Fetching and displaying images
Used [Glide library](https://github.com/bumptech/glide) for fetching and displaying images and also used its in built disk cache facility for image cache (clearing the cache in every 24 hrs).

## Custom Rating Bar view
Implemented this custom view using a combination of the native CircularProgressIndicator and TextView in a layout and created the custom view by inflating the layout. The custom view is capable to accept the rating value from xml or from code and can draw the rating indicator using the specified color depending on the rating value.
