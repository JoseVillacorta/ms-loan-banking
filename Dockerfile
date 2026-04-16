# Etapa 1: Construcción
FROM eclipse-temurin:21-jdk AS builder
WORKDIR /app

# Copiamos los archivos de gradle primero para cachear las dependencias
COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .

RUN chmod +x ./gradlew
RUN ./gradlew dependencies --no-daemon

# Copiamos el codigo fuente y compilamos
COPY src src
RUN ./gradlew build -x test --no-daemon

# Etapa 2: Ejecucion
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copiamos solo el JAR compilado desde la etapa anterior
COPY --from=builder /app/build/libs/*.jar app.jar

# El puerto de prestamos definido en el ms-loan.yml
EXPOSE 8084

ENTRYPOINT [ "java", "-jar", "app.jar"]