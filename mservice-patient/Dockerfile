#dockerfile for patient service, build maven before create docker image

FROM maven:3.9.3-eclipse-temurin-17 AS builder
WORKDIR /mservice-patient
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /mservice-patient
COPY --from=builder /mservice-patient/target/mservice-patient-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 9002
ENTRYPOINT ["java", "-jar", "app.jar"]