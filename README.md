# Football-manager
Football Manager is a comprehensive web application designed to manage football clubs and players
This project uses Spring Boot for the back-end infrastructure and Angular for the front-end UI.

# Features

- `Backend Functionality:`
  - Implements CRUD operations for managing teams and players in accordance with RESTful principles;
  - Offers a transfer operation for moving players between teams, with transfer costs calculated based on player experience and age;


- `Frontend Interface:`
  - Displaying a list of teams with basic information about each team with the ability to add/remove teams;
  - Displaying a list of all players with basic information about each player with the ability to add/remove players;
  - Access to a page with detailed information about the team and a list of its players (with the ability to go to the player's page), the ability to edit team data;
  - Access to a page with detailed information about the player, the ability to edit player data, as well as perform a transfer operation, go to the player's team page;
  - [Frontend video presentation](https://www.youtube.com/watch?v=nDA4TkuifIs&ab_channel=NadiaHuryk)
# Technologies
- Java `17`
- Spring Boot `3.1.1`
- MySQL `8.0.32`
- Docker `24.0.2`
- Liquibase `4.20.0`
- Lombok
- Maven `3.8.7`
- Angular: `16.1.7`
- Angular CLI: `16.1.6`


# How to Run
1. Install [Docker](https://www.docker.com/get-started) on your machine
2. Clone remote repository to your local machine
3. In the src/main/resources/application.properties and .env set your credentials
4. Build The application by running the Maven package command: mvn clean package
5. Run the command :
```bash
   docker-compose up --build
```
6. Open your browser at `http://localhost:4202`
7. After starting the application, you can access the Swagger UI documentation for your API by visiting [Swagger](http://localhost:6868/swagger-ui.html).