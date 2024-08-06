FROM openjdk:17-jdk-slim

# Defina o diretório de trabalho dentro do container
WORKDIR /app

# Copie o arquivo JAR da aplicação a partir da etapa de construção
COPY target/*.jar app.jar

# Expõe a porta 8080
EXPOSE 8082

# Defina o comando de inicialização da aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
