#dockerfile for user service, build maven before create docker image

FROM maven:3.9.3-eclipse-temurin-17 AS builder
WORKDIR /mservice-user
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /mservice-user
COPY --from=builder /mservice-user/target/mservice-user-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 9004
ENTRYPOINT ["java", "-jar", "app.jar"]