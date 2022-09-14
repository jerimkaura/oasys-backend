FROM openjdk:18
EXPOSE 8080
ADD target/oasis.jar oasis.jar
COPY ${JAR_FILE} myspringbootapp-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/oasis.jar"]