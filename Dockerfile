FROM maven:3.8.5-jdk-11-slim

WORKDIR notification-sender

COPY . .

RUN echo "$token"
ENTRYPOINT ["mvn", "-s", "maven-settings.xml", "install"]
