# Multi-stage Dockerfile: build with Maven, run with a lightweight JRE

# --- build stage ---
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /workspace
# copy only what is needed for a reproducible build
COPY pom.xml mvnw mvnw./cmd .mvn/ ./
COPY src ./src

# build the jar (skip tests for faster builds; remove -DskipTests to run tests)
RUN mvn -B -DskipTests package

# --- runtime stage ---
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# copy jar produced by maven
COPY --from=build /workspace/target/ModeConnect-0.0.1-SNAPSHOT.jar /app/app.jar

# create uploads directory (mounted as a volume in docker-compose)
RUN mkdir -p /app/uploads
VOLUME ["/app/uploads"]

EXPOSE 8080

# If you want to tune memory / other options, set JAVA_OPTS when running
ENV JAVA_OPTS=""

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]

