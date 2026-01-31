# --- Estágio 1: Build (Compilação) ---
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copia apenas o pom.xml primeiro para baixar dependências (cache eficiente)
COPY pom.xml .
# Baixa as dependências sem copiar o código fonte ainda
RUN mvn dependency:go-offline

# Copia o código fonte e faz o build
COPY src ./src
RUN mvn clean package -DskipTests

# --- Estágio 2: Runtime (Execução) ---
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copia o .jar gerado no estágio anterior
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta 8080
EXPOSE 8080

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]