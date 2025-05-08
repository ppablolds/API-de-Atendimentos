# Etapa de build com Maven
FROM maven:4.0.0-eclipse-temurin-21 AS build
WORKDIR /app

# Copia os arquivos do projeto
COPY pom.xml .
COPY src ./src

# Build do projeto
RUN mvn clean package -DskipTests

# Etapa de runtime com JDK leve
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copia o .jar gerado na etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta da aplicação (ajuste conforme necessário)
ENV SPRING_PROFILES_ACTIVE=prod
EXPOSE 8080

# Comando de inicialização
ENTRYPOINT ["java", "-jar", "app.jar"]

