#dockerfile for eureka service

FROM openjdk:17-jdk-slim
COPY target/eureka-server-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 9101
ENTRYPOINT ["java", "-jar", "app.jar"]