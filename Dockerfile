FROM openjdk:18
LABEL maintainer="jerimkaura001@gmail.com"
EXPOSE 8080
ADD target/oasis.jar oasis.jar
COPY ${JAR_FILE} myspringbootapp-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/oasis.jar"]