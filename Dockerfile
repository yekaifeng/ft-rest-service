FROM fabric8/java-centos-openjdk8-jdk:1.6.3
MAINTAINER Kennethy <yekaifeng@tom.com>

ENV JAVA_OPTIONS="-Xmx2048m -Xms1024m -XX:NewRatio=1 -XX:+UseConcMarkSweepGC -XX:CMSInitiatingOccupancyFraction=75 \
-XX:+UseCMSInitiatingOccupancyOnly -XX:ReservedCodeCacheSize=128M -XX:ParallelGCThreads=2 \
-XX:+ExplicitGCInvokesConcurrent -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom"
ENV JAVA_APP_JAR="gs-rest-service.jar"

USER root

EXPOSE 8800

COPY target/gs-rest-service-*.jar /deployments/${JAVA_APP_JAR}

RUN chown -R jboss /deployments/${JAVA_APP_JAR} \
  && chmod -R "g+rwX" /deployments/${JAVA_APP_JAR}\
  && chown -R jboss:root /deployments/${JAVA_APP_JAR}

USER jboss

WORKDIR ${JAVA_APP_DIR}

ENTRYPOINT [ "/deployments/run-java.sh" ]

# ENTRYPOINT [ "/deployments/run-java.sh", "--spring.profiles.active=${ENV}" ]

# ENTRYPOINT [ "sh", "-c", "java $APP_OPTS $JMX_OPTS $JAVA_OPTS $GCLOG_OPTS -Djava.security.egd=file:/dev/./urandom -jar ft-rest-service.jar" ]
