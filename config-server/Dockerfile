FROM eclipse-temurin:21-alpine
ARG APP_VERSION
ENV ARTIFACT_NAME=config-server-$APP_VERSION.jar
ENV ARTIFACT_PATH=build/libs/$ARTIFACT_NAME
WORKDIR /app
COPY $ARTIFACT_PATH entrypoint.sh ./
RUN ["chmod", "+x", "/app/entrypoint.sh"]
ENTRYPOINT ["/app/entrypoint.sh"]