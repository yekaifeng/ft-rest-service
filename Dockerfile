FROM registry.redhat.io/redhat-openjdk-18/openjdk18-openshift:latest
MAINTAINER Kennethye <kye@redhat.com>

ENV JAVA_OPTIONS="-Xmx2048m -Xms1024m -XX:NewRatio=1 -XX:+UseConcMarkSweepGC -XX:CMSInitiatingOccupancyFraction=75 \
-XX:+UseCMSInitiatingOccupancyOnly -XX:ReservedCodeCacheSize=128M -XX:ParallelGCThreads=2 \
-XX:+ExplicitGCInvokesConcurrent -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom \
${APP_OPTS} ${JMX_OPTS} ${GCLOG_OPTS}"
#ENV JAVA_APP_JAR="gs-rest-service.jar"

USER root
COPY . /tmp/src
RUN chown -R 185:0 /tmp/src
USER 185
RUN /usr/local/s2i/assemble

EXPOSE 8080
ENTRYPOINT [ "/opt/jboss/container/java/run/run-java.sh", "--spring.profiles.active=${SPRING_PROFILES_ACTIVE}" ]
