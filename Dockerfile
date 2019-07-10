# Start with a base image containing Java runtime
FROM openjdk:8-jdk-alpine

# Add Maintainer Info
LABEL maintainer="teckacademy@gmail.com"

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8080 available to the world outside this container
EXPOSE 8080

# The application's jar file
ARG JAR_FILE=build/libs/spring-boot-basics-master-1.0-SNAPSHOT.war
ARG PROPERTIES=src/main/resources/application.properties
ARG PROPERTIES_DEV=src/main/resources/application-dev.properties
ARG LOG=logback.xml

# Add the application's jar to the container
ADD ${JAR_FILE} /app/app.jar
ADD ${PROPERTIES} /app/application.properties
#RUN bash -c 'touch /app.jar'
# Run the jar file
#ENTRYPOINT ["java","-Dspring.config.location=./application.properties", "-jar","/app.jar"]
ENTRYPOINT ["java" ,"-Djava.security.egd=file:/dev/./urandom --spring.profile.active=dev","-jar","/app/app.jar"]

#ENV JAVA_OPTS=""
#ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]