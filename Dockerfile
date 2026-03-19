# Step 1: Build the JAR
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Step 2: Run the JAR
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
# Render provides a $PORT env var; Spring Boot must listen to it
ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=${PORT:8080}"]