FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
ADD ${JAR_FILE} crawler.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Djavax.net.debug=ssl -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=8888","-jar","/crawler.jar"]