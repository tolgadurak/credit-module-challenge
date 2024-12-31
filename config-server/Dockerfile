FROM eclipse-temurin:21 AS builder
ENV APP_HOME=/app
WORKDIR $APP_HOME
COPY . .
RUN rm -rf .git
RUN sh gradlew clean build


FROM eclipse-temurin:21
ARG APP_VERSION
ENV APP_HOME=/app
ENV ARTIFACT_NAME=config-server-$APP_VERSION.jar
ENV ARTIFACT_PATH=$APP_HOME/build/libs/$ARTIFACT_NAME
ENV ENTRYPOINT_SH_PATH=$APP_HOME/entrypoint.sh
WORKDIR $APP_HOME
COPY --from=builder $ARTIFACT_PATH $ENTRYPOINT_SH_PATH ./
ENTRYPOINT ["/app/entrypoint.sh"]