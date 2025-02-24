# Use an official Maven image to build the application
FROM maven:3.8.7-openjdk-18-slim AS build

# Set the working directory
WORKDIR /app

# Copy the pom.xml and source code
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean install

# Use a minimal JDK image to run the application
FROM openjdk:18-jdk-slim-bullseye

# Set the working directory
WORKDIR /app

# Copy the built jar file from the build stage
COPY --from=build /app/target/SupplierApplicationInsight-1.0.0.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]