# Usamos Java 21
FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

# Copiamos el JAR construido
COPY target/clientes-0.0.1-SNAPSHOT.jar app.jar

# Exponemos el puerto
EXPOSE 9025

# Comando para ejecutar la app
ENTRYPOINT ["java","-jar","app.jar"]
