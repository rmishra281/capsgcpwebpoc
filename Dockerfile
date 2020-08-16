FROM openjdk:8-jdk-alpine
COPY target/capsgcp*jar /app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
