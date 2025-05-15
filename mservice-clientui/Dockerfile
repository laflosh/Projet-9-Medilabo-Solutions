#dockerfile for clientui service, build maven before create docker image

FROM maven:3.9.3-eclipse-temurin-17 AS builder
WORKDIR /mservice-clientui
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /mservice-clientui
COPY --from=builder /mservice-clientui/target/mservice-clientui-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 9001
ENTRYPOINT ["java", "-jar", "app.jar"]