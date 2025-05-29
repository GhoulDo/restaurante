FROM openjdk:17-jdk-slim

# Crear usuario no-root para seguridad
RUN groupadd -r spring && useradd -r -g spring spring

# Establecer directorio de trabajo
WORKDIR /app

# Copiar archivos de configuración de Maven
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

# Dar permisos de ejecución al wrapper de Maven
RUN chmod +x ./mvnw

# Descargar dependencias (layer cache)
RUN ./mvnw dependency:go-offline -B

# Copiar código fuente
COPY src src

# Construir la aplicación
RUN ./mvnw clean package -DskipTests

# Cambiar propietario de archivos a usuario spring
RUN chown -R spring:spring /app

# Cambiar a usuario no-root
USER spring

# Exponer el puerto
EXPOSE 8081

# Variables de entorno por defecto
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8081/health || exit 1

# Comando para ejecutar la aplicación
CMD ["sh", "-c", "java $JAVA_OPTS -jar target/restaurante-0.0.1-SNAPSHOT.jar"]
