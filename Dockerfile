# Etapa 1: Build con Maven
FROM maven:3.9.6-eclipse-temurin-17 AS build

# Establece el directorio de trabajo
WORKDIR /app

# Copia el pom y las fuentes
COPY pom.xml .
COPY src ./src

# Compila y empaqueta la app (omitimos los tests para acelerar)
RUN mvn clean package -DskipTests

# Etapa 2: Imagen final con JDK
FROM eclipse-temurin:17-jdk

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el .jar desde la imagen de build
COPY --from=build app/target/shopverse-api-0.0.1-SNAPSHOT.jar .

# Expone el puerto 8081
EXPOSE 8081

# Comando por defecto para correr la app
ENTRYPOINT ["java", "-jar", "shopverse-api-0.0.1-SNAPSHOT.jar"]