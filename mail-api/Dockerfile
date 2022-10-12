FROM maven:3.8.6-openjdk-11-slim
WORKDIR /app

# multi-module executable jar
ARG JAR_FILE=target/*-exe.jar
COPY ${JAR_FILE} /app/app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
