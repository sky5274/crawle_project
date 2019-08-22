FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
ADD ${JAR_FILE} flow_config.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/flow_config.jar"]