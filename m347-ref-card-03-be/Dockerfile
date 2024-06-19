FROM openjdk:17-slim AS builder
WORKDIR /app
COPY src ./src
EXPOSE 8080
FROM openjdk:17-slim
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
