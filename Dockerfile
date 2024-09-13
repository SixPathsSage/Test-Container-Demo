# Start with a base image containing Java runtime
FROM openjdk:21

# Set the working directory inside the container
WORKDIR /app

# Copy the built jar file into the container
COPY target/app.jar app.jar

# Expose the port the app runs on
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
