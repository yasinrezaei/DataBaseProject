FROM openjdk:8
COPY . /tmp
WORKDIR /tmp
CMD ["java", "-jar", "JavaPassFromConsole.jar"]