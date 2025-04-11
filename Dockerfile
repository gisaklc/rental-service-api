# Etapa de construção com Maven e JDK 21
FROM maven:3.9.9-openjdk-21 as builder

WORKDIR /app
COPY . . 
RUN mvn dependency:resolve
RUN mvn clean package -DskipTests

# Etapa de execução com Amazon Corretto JDK 21
FROM amazoncorretto:21

WORKDIR /app
COPY --from=builder ./app/target/*.jar ./rental-service-api.jar
EXPOSE 8080

ENV POSTGRES_HOST=db
ENV DATASOURCE_URL=''
ENV DATASOURCE_USERNAME=''
ENV DATASOURCE_URL=''
ENV GOOGLE_CLIENT_ID='client_id'
ENV GOOGLE_CLIENT_SECRET='client_id'

ENV SPRING_PROFILES_ACTIVE='production'
ENV TZ='America/Sao_Paulo'

RUN echo "the env var POSTGRES_HOST value is $POSTGRES_HOST"

ENTRYPOINT java -jar rental-service-api.jar
