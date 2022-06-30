FROM openjdk:11-jdk
EXPOSE 8050
WORKDIR /app

# Copy maven executable to the image
COPY mvnw .
COPY .mvn .mvn


# Copy the pom.xml file
COPY ./pom.xml ./pom.xml

RUN chmod 755 /app/mvnw
RUN ./mvnw dependency:go-offline -B -Dmaven.artifact.threads=35


# Copy the project source
COPY ./src ./src


RUN ./mvnw  package

ENTRYPOINT ["java","-jar","target/TransactionService-0.0.1-SNAPSHOT.jar"]