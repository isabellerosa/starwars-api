FROM openjdk:11.0.7-jdk-slim as application
COPY . /starwars-api
WORKDIR /starwars-api

RUN ./mvnw install -DskipTests && mv target/*.jar app.jar
EXPOSE 8080
CMD ["java","-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]
