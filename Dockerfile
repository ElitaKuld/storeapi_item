FROM amazoncorretto:17-alpine

# Kopiera projektets filer
VOLUME /tmp

# Bygg projektet med Gradle
ARG JAR_FILE=build/libs/storeapi_item.jar
COPY ${JAR_FILE} app.jar

# Exponera port och ange startkommando
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]