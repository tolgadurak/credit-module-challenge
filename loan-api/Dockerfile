FROM eclipse-temurin:21-alpine
ARG APP_VERSION
ENV ARTIFACT_NAME=loan-api-$APP_VERSION.jar
ENV ARTIFACT_PATH=build/libs/$ARTIFACT_NAME
WORKDIR /app
COPY $ARTIFACT_PATH entrypoint.sh ./
ENTRYPOINT ["/app/entrypoint.sh"]