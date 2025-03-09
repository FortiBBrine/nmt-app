FROM openjdk:17-jdk-slim
WORKDIR /app
COPY ./backend .
RUN apt update 
RUN apt install -y findutils
RUN ./gradlew bootJar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "build/libs/NmtApp.jar"]
