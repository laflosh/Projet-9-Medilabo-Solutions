#dockerfile for note service

FROM openjdk:17-jdk-slim
COPY target/mservice-note-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 9003
ENTRYPOINT ["java", "-jar", "app.jar"]