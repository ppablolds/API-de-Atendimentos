# Usa a imagem do OpenJDK 21 baseada em Alpine (leve e eficiente)
FROM eclipse-temurin:21-jdk-alpine

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia o JAR gerado pelo Maven para dentro do container
COPY target/*.jar app.jar

# Expõe a porta padrão usada pelo Spring Boot
EXPOSE 8080

# Comando para rodar o app
ENTRYPOINT ["java", "-jar", "app.jar"]
