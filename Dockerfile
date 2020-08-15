FROM openjdk:14-alpine
COPY build/libs/datediff-*-all.jar datediff.jar
EXPOSE 8080
CMD ["java", "-Dcom.sun.management.jmxremote", "-Xmx128m", "-jar", "datediff.jar"]