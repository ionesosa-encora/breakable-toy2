# Usa la imagen base de OpenJDK con Gradle preinstalado (Gradle 8.11.1 y JDK 21)
FROM gradle:8.11.1-jdk21 AS build

# Establece el directorio de trabajo en el contenedor
WORKDIR /app

# Copia los archivos necesarios para construir la aplicación
COPY build.gradle settings.gradle gradlew /app/
COPY gradle /app/gradle
COPY src /app/src

# Construye la aplicación usando Gradle
RUN ./gradlew build --no-daemon

# Usaremos una imagen más ligera para ejecutar la aplicación
FROM openjdk:21-jdk-slim

# Establece el directorio de trabajo para el runtime
WORKDIR /app

# Copia los certificados al contenedor
COPY spotify_cert.pem /etc/ssl/certs/spotify_cert.pem
COPY zscaler_root.pem /etc/ssl/certs/zscaler_root.pem

# Instala los certificados en el truststore de Java con alias únicos
RUN apt-get update && apt-get install -y --no-install-recommends ca-certificates \
    && keytool -importcert -trustcacerts -file /etc/ssl/certs/spotify_cert.pem \
       -keystore $JAVA_HOME/lib/security/cacerts -alias spotify-cert -storepass changeit -noprompt \
    && keytool -importcert -trustcacerts -file /etc/ssl/certs/zscaler_root.pem \
       -keystore $JAVA_HOME/lib/security/cacerts -alias zscaler-cert -storepass changeit -noprompt \
    && rm -rf /var/lib/apt/lists/*

# Copia el archivo JAR construido en la etapa anterior
COPY --from=build /app/build/libs/*.jar app.jar

# Expone el puerto 8080 para que el backend esté accesible
EXPOSE 8080

# Comando para ejecutar la aplicación Spring Boot
CMD ["java", "-jar", "app.jar"]
