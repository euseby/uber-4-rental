# Etapa 1: Construirea aplicatiei (Build)
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copiem doar pom.xml la inceput pentru a descarca dependintele mai rapid (caching)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiem restul codului sursa si compilam aplicatia
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Rularea aplicatiei (Run)
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Copiem doar fisierul .jar compilat din etapa precedenta pentru a avea un container curat si mic
COPY --from=build /app/target/uber4rental-1.0-SNAPSHOT.jar app.jar

# Expunem portul 8080 (portul default Spring Boot)
EXPOSE 8080

# Comanda care porneste serverul
ENTRYPOINT ["java", "-jar", "app.jar"]
