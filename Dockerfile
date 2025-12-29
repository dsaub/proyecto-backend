FROM alpine:edge AS builder
WORKDIR /build
ADD . .

RUN apk add openjdk25-jdk maven
RUN mvn clean compile package -DskipTests

FROM alpine:edge AS runtime
WORKDIR /app
RUN apk update
RUN apk upgrade --no-cache busybox
RUN apk add --no-cache openjdk25-jre-headless
RUN apk add --no-cache curl
RUN rm -rf /var/cache/apk/*
COPY --from=builder /build/target/proyecto-backend-0.0.2-SNAPSHOT.jar /app/app.jar
ENTRYPOINT [ "java", "-jar", "/app/app.jar"]
