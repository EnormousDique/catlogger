FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY cat-logger.jar ./cat-logger.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/cat-logger.jar"]