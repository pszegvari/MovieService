FROM openjdk:17-jdk-slim

WORKDIR /app

COPY wait-for-it.sh /app/
RUN chmod +x /app/wait-for-it.sh

COPY target/movieservice-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

# Use wait-for-it to wait for MySQL to be ready before starting the application
ENTRYPOINT ["./wait-for-it.sh", "db:9906", "--", "java", "-jar", "app.jar"]