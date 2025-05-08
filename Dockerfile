# Etapa de build com Maven
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Instalação manual do Maven 4.0.0
RUN curl -fsSL https://downloads.apache.org/maven/maven-4/4.0.0-alpha-7/binaries/apache-maven-4.0.0-alpha-7-bin.tar.gz | tar -xz
RUN mv apache-maven-4.0.0-alpha-7 /opt/maven
ENV MAVEN_HOME=/opt/maven
ENV PATH=$MAVEN_HOME/bin:$PATH

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

