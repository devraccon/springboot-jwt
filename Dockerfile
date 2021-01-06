# Start with a base image containing Java runtime
FROM java:8

# Add Author info
LABEL maintainer="kang3351@naver.com"

# Add a volume to /tmp
VOLUME /tmp

# Make port 8080 available to the world outside this container
EXPOSE 8080

# The application's jar file
ARG JAR_FILE

# Add the application's jar to the container
COPY ${JAR_FILE} my_react_app.jar

# Run the jar file
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=local","/my_react_app.jar"]