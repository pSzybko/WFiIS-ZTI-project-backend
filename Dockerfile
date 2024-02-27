FROM maven:3.8.1-openjdk-17-slim AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17
COPY --from=build /target/projekt-0.0.1-SNAPSHOT.war projekt-0.0.1-SNAPSHOT.war
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "projekt-0.0.1-SNAPSHOT.war"]
