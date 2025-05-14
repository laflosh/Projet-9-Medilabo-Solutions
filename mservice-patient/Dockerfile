#dockerfile for patient service

FROM openjdk:17-jdk-slim
COPY target/mservice-patient-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 9002
ENTRYPOINT ["java", "-jar", "app.jar"]