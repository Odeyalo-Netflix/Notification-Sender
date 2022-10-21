FROM maven:3.8.5-jdk-11-slim

WORKDIR notification-sender

COPY . .

ENTRYPOINT mvn -s maven-settings.xml spring-boot:run
