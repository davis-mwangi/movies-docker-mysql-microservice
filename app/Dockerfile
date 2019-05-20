FROM maven:3.5-jdk-8

#FROM java:8
#
#EXPOSE 8081
#
#ADD /target/movie-0.0.1-SNAPSHOT.jar /movie-0.0.1-SNAPSHOT.jar
#
#ENTRYPOINT ["java", "-jar", "movie-0.0.1-SNAPSHOT.jar"]

## Start with a base image containing Java runtime
#FROM java:8
## Add Maintainer Info
#LABEL maintainer="davidmwangi0902@gmail.com"
## Add a volume pointing to /tmp
#VOLUME /tmp
## Make port 8081 available to the world outside this container
#EXPOSE 8081
## The application's jar file
##ARG JAR_FILE=target/movie-0.0.1-SNAPSHOT.jar
### Add the application's jar to the container
##ADD ${JAR_FILE} movie-0.0.1-SNAPSHOT.jar
#
#ADD /target/movie-0.0.1-SNAPSHOT.jar movie-0.0.1-SNAPSHOT.jar
## Run the jar file
#ENTRYPOINT ["java","-jar","movie-0.0.1-SNAPSHOT.jar"]