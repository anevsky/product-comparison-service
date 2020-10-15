#!/usr/bin/env sh

echo "### starting"
cp app/product-comparison-service-1.0-SNAPSHOT.jar ./app.jar
exec java -agentlib:jdwp=transport=dt_socket,server=y,address=11112,suspend=n \
  -Djava.security.egd=file:/dev/./urandom \
  -jar /app.jar