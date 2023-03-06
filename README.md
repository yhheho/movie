## Environment
- Develop in Java 8.
- Use Gradle 7.6.2 as build tool
- [Postman](https://www.postman.com/downloads/) is strongly recommended to run tests against this application.
- H2 Database (in-memory database) is used as the database.
  - To see data, you can go to http://localhost:8080/h2-console on a browser

## How to start the application
- Download this project and make sure Gradle 7.6 is installed.
  - [How to install gradle](https://docs.gradle.org/current/userguide/installation.html)
- cd into this project and `./gradlew clean build`
- Run `./gradlew bootRun`
- After seeing success logs, you can send requests to `http://localhost:8080`. (default url and port)

## Assumptions
- There will only be three users (no signup endpoint, but you can insert user data to the database directly):
  - username: admin, pass: 123, ROLE = ADMIN
  - username: m1, pass: 123, ROLE = USER
  - username: m2, pass: 123, ROLE = USER
- There will be 12 movies inserted at application start up (please see /src/resources/db/migration files)
- There will be some favorite inserted into table at application start up (please see /src/resources/db/migration files)
- There is a Postman collection file "Monstar lab Movie.postman_collection.json" in the root folder for testing the APIs.

## APIs
### Auth
- This service provides single signin/singout functions to support viewing a user's favorites
- Three users are populated in the /resource/db/migration files.
- User has its own `ROLE`  

#### POST /api/auth/signin
   The signin API is used to authenticate a user with their username and password. 
   A JWT token is generated on successful authentication, which is then set as a cookie in the response header.

- Request Body:
  - username (required, string): The username of the user to be authenticated.
  - password (required, string): The password of the user to be authenticated.
- Response Body:
  - username (string): The username of the authenticated user.
  - email (string): The email of the authenticated user.
  - roles (array of strings): The roles of the authenticated user.
- Response Headers:
  - Set-Cookie (string): A cookie containing the JWT token.

#### POST /api/auth/signout:
signout function, will set cookie to empty

- Response Body:
  - message (string): A message indicating that the user has been signed out.
- Response Headers:
  - Set-Cookie (string): A cookie with an empty JWT token, indicating that the user is no longer authenticated.

### User

#### GET /api/users
get all users.

- Only ADMIN ROLE can access this endpoint.
- Query Parameters:
  - page (integer, default: 0): The page number of the results to retrieve.
  - size (integer, default: 10): The number of results to retrieve per page.
- Response Body:
  - hasNextPage (boolean): Indicates whether there are more pages of results.
  - users (array of objects): A list of user objects containing information about each user.
- Response Status Codes:
  - 200 OK: Returned on success.
  - 403 Forbidden: Returned if the user making the request does not have an ADMIN role.
  - 500 Internal Server Error: Returned if an error occurs on the server.

#### GET /api/users/{user_id}
get details of a user.

- Only signined user can access this endpoint
- Path Parameters:
  - id (required, integer): The ID of the user to retrieve.
- Response Body:
  - id (integer): The ID of the user.
  - username (string): The username of the user.
  - email (string): The email of the user.
  - roles (array of strings): The roles of the user.
- Response Status Codes:
  - 200 OK: Returned on success.
  - 403 Forbidden: Returned if the user making the request does not have a USER or ADMIN role.
  - 404 Not Found: Returned if the requested user does not exist.
  - 500 Internal Server Error: Returned if an error occurs on the server.

### Movie

#### GET /api/movies
get movie list, can get movie by the search term (only title, release_date, type supported)

- This endpoint can be accessed by anyone
- Path parameters:
  - page (optional): The page number to retrieve, defaults to 0.
  - size (optional): The number of movies to retrieve per page, defaults to 10.
  - search (optional): A string to search for in movie titles.
    - search can only be the following:
      - search=title={movie_name}, ex: search=title=Titanic.
      - search=release_date={release date, YYYY-MM-DD format}, ex: search=release_date=1999-10-23.
        - the release_date search will retrieve all movies that are earlier than the given release_date.
      - search=type={movie type}, only `ACTION`, `ROMANTIC`, `DOCUMENT`, `HORROR`.
  - sort (optional): The sort order of the results, either `asc` for ascending or `desc` for descending, defaults to `asc`.
    - sort value other than `asc`, `desc` will have no effects.
- Response Body:
  - hasNextPage: the result is a page, you can use this to retrieve next page.
  - list of Movie:
    - Movie:
      - id (integer): The ID of the movie.
      - title (string): the title of the movie.
      - description (string): the description of the movie.
      - type (string): the type of the movie. only `ACTION`, `ROMANTIC`, `DOCUMENT`, `HORROR`.
      - releaseDate (string): the release date of the movie

#### GET /api/movies/{movie_id}
get details of a movie

- This endpoint can be accessed by anyone
- Path parameter:
  - movie_id (required, integer): the movie id of the movie you want to retrieve.
- Response Body:
  - hasNextPage: the result is a page, you can use this to retrieve next page.
  - list of Movie:
    - Movie:
      - id (integer): The ID of the movie.
      - title (string): the title of the movie.
      - description (string): the description of the movie.
      - type (string): the type of the movie. only `ACTION`, `ROMANTIC`, `DOCUMENT`, `HORROR`.
      - releaseDate (string): the release date of the movie

#### POST /api/movies
create a new movie

- Only USER or ADMIN role can access this endpoint
- Request
  - title (string): the title of the movie.
  - description (string): the description of the movie.
  - type (string): the type of the movie. only `ACTION`, `ROMANTIC`, `DOCUMENT`, `HORROR`.
  - releaseDate (string): the release date of the movie
- Response Body
  - Movie:
    - id (integer): The ID of the movie.
    - title (string): the title of the movie.
    - description (string): the description of the movie.
    - type (string): the type of the movie. only `ACTION`, `ROMANTIC`, `DOCUMENT`, `HORROR`.
    - releaseDate (string): the release date of the movie

#### PUT /api/movies/{movie_id}
update a movie

- Only USER or ADMIN role can access this endpoint
- Path parameter:
  - movie_id (required, integer): the movie id of the movie you want to update.
- Request
  - title (string): the title of the movie.
  - description (string): the description of the movie.
  - type (string): the type of the movie. only `ACTION`, `ROMANTIC`, `DOCUMENT`, `HORROR`.
  - releaseDate (string): the release date of the movie
- Response Body
  - Movie:
    - id (integer): The ID of the movie.
    - title (string): the title of the movie.
    - description (string): the description of the movie.
    - type (string): the type of the movie. only `ACTION`, `ROMANTIC`, `DOCUMENT`, `HORROR`.
    - releaseDate (string): the release date of the movie

#### DELETE /api/movies/{movie_id}
delete a movie

- Only ADMIN role can access this endpoint
- Path parameter:
  - movie_id (required, integer): the id of the movie to be deleted
- Response Status Codes:
  - 200 OK: Returned on success.


### Favorite
The path of favorite is under /api/users

#### GET /api/users/{user_id}/favorites
Retrieve all favorite of a user.

- Only USER or ADMIN role can access this endpoint
- Path parameter:
  - user_id (required, integer): the user id
  - page (optional): The page number to retrieve, defaults to 0.
  - size (optional): The number of movies to retrieve per page, defaults to 10.
- Response body:
  - hasNextPage: the result is a page, you can use this to retrieve next page.
  - list of Favorite: 
    - Favorite:
      - userId (integer): the user id of this favorite
      - movieId (integer): the movie id of this favorite
      - movieTitle (string): the title of the favorite movie

#### POST /api/users/{user_id}/favorites
create a new favorite of a user.

- Only USER or ADMIN role can access this endpoint
- Path parameter:
  - user_id (required, integer): the user id
- Request:
  - movieId (integer): the movie id to be favorite by the user.
- Response Body:
  - Favorite:
    - userId (integer): the user id of this favorite
    - movieId (integer): the movie id of this favorite
    - movieTitle (string): the title of the favorite movie

#### DELETE /api/users/{user_id}/favorites/{movie_id}
delete a favorite by movie id

- Only USER or ADMIN role can access this endpoint
- Path parameter:
  - user_id (required, integer): the user id
  - movie_id (required, integer): the movie id
- Response Status Codes:
  - 200 OK: Returned on success.