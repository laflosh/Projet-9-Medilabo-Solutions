#dockerfile for gateway service, build maven before create docker image

FROM maven:3.9.3-eclipse-temurin-17 AS builder
WORKDIR /gateway-server
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /gateway-server
COPY --from=builder /gateway-server/target/gateway-server-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]