# Build stage
FROM maven:3.9.8-eclipse-temurin-11 AS build
WORKDIR /app

# Allow HTTP repositories blocked by Maven 3.9+ (needed for JasperReports deps)
RUN mkdir -p /root/.m2 && \
    echo '<?xml version="1.0" encoding="UTF-8"?>\
<settings xmlns="http://maven.apache.org/SETTINGS/1.2.0"\
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"\
  xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.2.0 https://maven.apache.org/xsd/settings-1.2.0.xsd">\
  <mirrors>\
    <mirror>\
      <id>jaspersoft-third-party</id>\
      <mirrorOf>jaspersoft-third-party</mirrorOf>\
      <url>https://jaspersoft.jfrog.io/jaspersoft/third-party-ce-artifacts/</url>\
    </mirror>\
    <mirror>\
      <id>jr-ce-releases</id>\
      <mirrorOf>jr-ce-releases</mirrorOf>\
      <url>https://jaspersoft.jfrog.io/jaspersoft/jr-ce-releases</url>\
    </mirror>\
  </mirrors>\
</settings>' > /root/.m2/settings.xml

COPY . .
RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin:11-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

