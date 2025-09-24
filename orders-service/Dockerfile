# ---- Build stage ----
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /workspace

# 1) Copy parent + ALL module POMs (reactor aware)
COPY pom.xml .
COPY oms-common-domain/pom.xml         oms-common-domain/pom.xml
COPY oms-common-events/pom.xml         oms-common-events/pom.xml
COPY orders-service/pom.xml            orders-service/pom.xml
COPY payment-service/pom.xml           payment-service/pom.xml
COPY inventory-service/pom.xml         inventory-service/pom.xml
COPY notification-service/pom.xml      notification-service/pom.xml

# 2) Copy sources ONLY for the modules we need to build
COPY oms-common-domain/                oms-common-domain/
COPY oms-common-events/                oms-common-events/
COPY orders-service/                   orders-service/

# 3) Build just these modules in one shot (reactor builds deps)
RUN mvn -q -DskipTests -f pom.xml -pl oms-common-domain,oms-common-events,orders-service -am package

# ---- Runtime stage ----
FROM eclipse-temurin:21-jre
WORKDIR /app
ENV JAVA_TOOL_OPTIONS="-XX:MaxRAMPercentage=75 -XX:+HeapDumpOnOutOfMemoryError -XX:+UseContainerSupport"
COPY --from=build /workspace/orders-service/target/orders-service-*.jar /app/app.jar
EXPOSE 8081
USER 10001
ENTRYPOINT ["java","-jar","/app/app.jar"]
