# Home Assignment

## Prerequisites

To run this application outside of an IDE, you will need to install Docker on your computer. Follow the installation instructions from the official Docker documentation:  
[Docker Compose Installation Guide](https://docs.docker.com/compose/install/)

## Setup and Running the Application

1. **Build the Application:**
    - Open a terminal and navigate to the root directory of the project.
    - Run the following command to compile the application:
      ```bash
      mvn clean package
      ```
    - This will generate the compiled application in the `target` directory.

2. **Build the Docker Image:**
    - Next, execute the following command to build the Docker image using the provided `Dockerfile`:
      ```bash
      docker-compose build
      ```

3. **Start the Application:**
    - The `docker-compose.yml` file configures a MySQL and a Redis server instance. 
    - The setup only needs an API key for OMDB and TMDB. Please fill the two variable available at the bottom of the file.
    - Run the following command to execute the startup:
      ```bash
      docker-compose up
      ```
    - During startup, the application will initialize the database and cache server in the background. Once the process is complete, you can access the movie service.

4. **Validating the effect of caching**
    - If you run the same request multiple times, you will see in the log that the first response time is significantly longer than the following ones.


## Accessing the Application

- **API Documentation:**  
  The available endpoints can be accessed via the OpenAPI definition at the following URL:  
  [Swagger UI - http://localhost:8080/swagger-ui](http://localhost:8080/swagger-ui)

- **Testing with Postman:**  
  You can also test the application using the provided Postman collection located in the postman directory. 

#### NOTE
- When running in the IDE, do not forget to add the API keys to you environment variables or define them in `application-local.yaml` 