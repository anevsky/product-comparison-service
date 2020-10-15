FROM openjdk:11.0.7-jre
LABEL maintainer="anwer.man@gmail.com"
RUN groupadd alex && \
    useradd -g alex -m -s /bin/false alex
COPY --chown=alex:alex build/libs/product-comparison-service-1.0-SNAPSHOT.jar ./app.jar
USER alex
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]