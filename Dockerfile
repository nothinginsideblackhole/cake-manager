FROM openjdk:13-jdk-alpine
MAINTAINER santoshr
COPY build/libs/cake-manager-0.0.1.jar cake-manager-0.0.1.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/cake-manager-0.0.1.jar"]