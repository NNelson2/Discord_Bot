FROM openjdk:17-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ./target/bot-1.0-SNAPSHOT-jar-with-dependencies.jar app.jar
ENTRYPOINT ["java","-jar", "/app.jar"]
