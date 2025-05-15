#dockerfile for eureka service, build maven before create docker image

FROM maven:3.9.3-eclipse-temurin-17 AS builder
WORKDIR /eureka-server
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /eureka-server
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*
COPY --from=builder /eureka-server/target/eureka-server-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 9101
ENTRYPOINT ["java", "-jar", "app.jar"]