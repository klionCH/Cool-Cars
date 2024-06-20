FROM maven:3.8.4-openjdk-17 AS builder
WORKDIR /build
COPY . /build
RUN mvn -B clean package
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /build/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
