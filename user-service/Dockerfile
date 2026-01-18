FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Diretório Maven gravável
ENV MAVEN_CONFIG=/tmp/.m2
RUN mkdir -p /tmp/.m2

COPY pom.xml .
RUN mvn -Dmaven.repo.local=/tmp/.m2 dependency:go-offline

COPY src ./src
RUN mvn -Dmaven.repo.local=/tmp/.m2 clean package -DskipTests

FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
