FROM openjdk11-apm-es7-8.5.0
RUN apt-get update && apt-get install jq curl telnet -y && mkdir -p /app/

COPY target/*.jar /app/application.jar

WORKDIR /app
ENTRYPOINT ["java","-jar","/app/application.jar"]
