FROM eclipse-temurin:21-jdk-alpine
RUN apk add --no-cache maven
WORKDIR /app/back
COPY . .
# COPY target/mysqltest-0.0.1-SNAPSHOT.jar mysqltest-0.0.1-SNAPSHOT.jar
# CMD ["java", "-jar", "mysqltest-0.0.1-SNAPSHOT.jar"]
CMD ["mvn", "spring-boot:run"]