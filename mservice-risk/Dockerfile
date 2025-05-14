#dockerfile for risk service

FROM openjdk:17-jdk-slim
COPY target/mservice-risk-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 9005
ENTRYPOINT ["java", "-jar", "app.jar"]