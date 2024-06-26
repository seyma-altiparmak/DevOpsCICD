# Use OpenJDK 11 as base image
FROM openjdk:17-alpine
EXPOSE 9090
# Copy the packaged Spring Boot application JAR file into the container at /app
COPY build/libs/DevOpsCICD-1.0-SNAPSHOT.jar /DevOpsCICD.jar

# Specify the command to run your Spring Boot application when the container starts
CMD ["java", "-jar", "/DevOpsCICD.jar"]