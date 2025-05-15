#dockerfile for note service, build maven before create docker image

FROM maven:3.9.3-eclipse-temurin-17 AS builder
WORKDIR /mservice-note
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /mservice-note
COPY --from=builder /mservice-note/target/mservice-note-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 9003
ENTRYPOINT ["java", "-jar", "app.jar"]