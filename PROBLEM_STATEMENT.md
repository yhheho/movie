# Code challenge - Backend

## Intro

A fictitious client have asked us to build an app where their customers are able to search for films, see details, favorite etc. To achieve this, our app developer needs an in-house developed API to be able to fulfill the customer's current and future requirements.

## Requirements

Build a RESTful JSON API, that supports:  
**A)** Getting a list of movies filterable by a query  
**B)** Details for a specific movie.  
**C)** Favorite a specific movie.  
**D)** Get a list of movies marked as favorite.

Your API should expose the following endpoints:  
`GET /movies?search={search}` - return popular movies or what the user searched for  
`GET /movies/:id` - return that specific movie in detail  
`GET /favorites` - return movies that the user has previously marked as favorite.  
`POST /favorites/:id` - add a favorite movie  

- Please make sure that the `favorite` resource is only accessible by the users themselves.
- The `movie` resource can be accessible by anyone.

To support all the requirements, there may be more endpoints needed than the ones stated above.

Lastly, please chose a language/framework from the list below.
- Go (Golang)
- Node.js
- Ruby (Ruby on Rails)
- Java (Spring Boot \Spring）
- PHP (Laravel）

## Expectations

- You are free to design the interface you are exposing.
- You are also free to use any storage or data sources you like and you are free to decide on internal architecture. 
- Your code should be testable and should follow recognized coding standards as far as this is practical.

This challenge is intentionally left relatively 'open' - we give you only enough information to get started. You may need to make some assumptions and decisions to get going. Please let us know what these assumptions are as this will be helpful when we evaluate your response.

## How to deliver your code

 - We prefer that you use git even though you shouldn't make your code public, that way we can track progress and the way you work
 - Push to a private repository we invited
 - Include a small guide on setting up the environment for testing
 - Send it to your contact person as a link to a hosted location or if the zip is small enough, attach it.

## Brownie points

- If you add a solution for the problem: "It works on my machine! `¯\_(ツ)_/¯`"
- Follow industry best practices on API documentation.
