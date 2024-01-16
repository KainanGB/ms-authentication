FROM maven:3.9.2-eclipse-temurin-17-alpine AS build

ARG ENV_EUREKA_SERVICE_URL
ARG ENV_KAFKA_SERVER_1
ARG ENV_KAFKA_SERVER_2
ARG ENV_KAFKA_SERVER_3
ARG ENV_DATA_SOURCE_URL
ARG ENV_DATA_SOURCE_USERNAME
ARG ENV_DATA_SOURCE_PASSWORD
ARG ENV_JWT_SECRET

ENV EUREKA_SERVICE_URL=${EUREKA_SERVICE_URL}
ENV KAFKA_SERVER_1=${KAFKA_SERVER_1}
ENV KAFKA_SERVER_2=${KAFKA_SERVER_2}
ENV KAFKA_SERVER_3=${KAFKA_SERVER_3}
ENV DATA_SOURCE_URL=${DATA_SOURCE_URL}
ENV DATA_SOURCE_USERNAME=${DATA_SOURCE_USERNAME}
ENV DATA_SOURCE_PASSWORD=${DATA_SOURCE_PASSWORD}
ENV JWT_SECRET=${JWT_SECRET}

WORKDIR /app

COPY .mvn/ .mvn
COPY pom.xml ./
RUN mvn dependency:go-offline
COPY ./src ./src

RUN mvn clean package -Dmaven.test.skip.exec

## BUILD JAR

FROM eclipse-temurin:17-alpine


COPY --from=build ./app/target/*.jar ./ms-auth.jar

CMD ["java", "-jar", "ms-auth.jar"]

# docker build --build-arg DATA_SOURCE_URL=jdb
# c:mysql://172.20.0.1:3306/users --build-arg DATA_SOURCE_USERNAME=root --build-arg DATA_SOURCE_PASSWORD=
# root --build-arg EUREKA_SERVICE_URL=http://172.20.0.1:8761/eureka/ --build-arg JWT_SECRET=1234 --build-
# arg KAFKA_SERVER_1=http://172.20.0.1:9091 --build-arg KAFKA_SERVER_2=http://172.20.0.1:9093 --build-arg
#  KAFKA_SERVER_3=http://172.20.0.1:9094 . -t platform/authenticationAFKA_SERVER_2=localhost:9093 --build-arg KAFKA_SERVER_3=localhost:9094