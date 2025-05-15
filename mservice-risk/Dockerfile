#dockerfile for risk service, build maven before create docker image

FROM maven:3.9.3-eclipse-temurin-17 AS builder
WORKDIR /mservice-risk
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /mservice-risk
COPY --from=builder /mservice-risk/target/mservice-risk-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 9005
ENTRYPOINT ["java", "-jar", "app.jar"]