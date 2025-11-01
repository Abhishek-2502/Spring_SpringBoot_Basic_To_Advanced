# Use a Java 21 JDK image to build the Spring Boot app
FROM eclipse-temurin:21-jdk AS build

# Set the working directory
WORKDIR /app

# Copy Maven wrapper and POM to take advantage of caching
COPY mvnw ./
COPY .mvn/ .mvn/
COPY pom.xml ./

# Ensure the Maven wrapper is executable
RUN chmod +x mvnw

# Download dependencies to cache them
RUN ./mvnw dependency:go-offline

# Copy the source code and build the application
COPY src ./src
COPY init-db ./init-db
RUN ./mvnw clean package -DskipTests

# Use a Java 23 runtime image to run the application
FROM eclipse-temurin:23-jre

# Set the working directory
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port 8081 for the Spring Boot application
EXPOSE 8081

# Specify the command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]


# Build the Docker image
# docker build -t rag-backend .

# Run the container, mapping port 8081 on host to 8081 in container
# docker run -d -p 8081:8081 --name rag-backend-container rag-backend

# Tag the image for Docker Hub
# docker tag rag-backend abhi25022004/rag-backend:latest

# Push the image to Docker Hub
# docker push abhi25022004/rag-backend:latest