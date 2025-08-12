FROM eclipse-temurin:21
LABEL maintainer="d.torquato.dev@gmail.com"
WORKDIR /app
COPY target/MagicFridgeAi-0.0.1-SNAPSHOT.jar /app/MagicFridgeAi-docker.jar
ENTRYPOINT ["java","-jar","MagicFridgeAi-docker.jar"]
