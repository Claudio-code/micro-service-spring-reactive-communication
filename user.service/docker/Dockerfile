FROM gradle:jdk17 as builder

WORKDIR /builder

COPY build.gradle .
COPY settings.gradle .
COPY src src
COPY gradlew gradlew
COPY gradle gradle

RUN ./gradlew build -x test --no-daemon

FROM openjdk:17-slim-bullseye

COPY --from=builder /builder/build/libs/user.service-0.0.1-SNAPSHOT.jar /app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
