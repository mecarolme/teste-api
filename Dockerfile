FROM openjdk:21-jdk-slim

WORKDIR /app

COPY . /app

RUN chmod +x ./mvnw

RUN ./mvnw clean install -DskipTests

EXPOSE 8080

CMD ["java", "-jar", "target/*.jar"]
