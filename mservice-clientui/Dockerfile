#dockerfile for clientui service

FROM openjdk:17-jdk-slim
COPY target/mservice-clientui-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 9001
ENTRYPOINT ["java", "-jar", "app.jar"]