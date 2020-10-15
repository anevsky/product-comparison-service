FROM adoptopenjdk/openjdk11:jdk-11.0.5_10-alpine
LABEL maintainer="anwer.man@gmail.com"
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=build/libs/product-comparison-service-1.0-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]