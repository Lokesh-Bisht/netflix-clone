# Build Stage
FROM maven:3.8.7-eclipse-temurin-17-alpine AS build
COPY ./pom.xml ./pom.xml
RUN mvn dependency:go-offline -B
COPY ./src ./src
RUN mvn clean package

# Package Stage
FROM eclipse-temurin:17-jdk-alpine
COPY --from=build /target/GenreService-1.0.jar /GenreService-1.0.jar
RUN chmod 777 /var/log/
EXPOSE 8080
CMD ["java","-jar","/GenreService-1.0.jar"]