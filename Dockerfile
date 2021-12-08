FROM registry.redhat.io/redhat-openjdk-18/openjdk18-openshift:latest
MAINTAINER Kennethye <kye@redhat.com>

ENV JAVA_OPTS="-Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom"

USER root
COPY . /tmp/src
RUN chown -R 185:0 /tmp/src
USER 185
RUN /usr/local/s2i/assemble

EXPOSE 8080
ENTRYPOINT [ "/opt/jboss/container/java/run/run-java.sh", "--spring.profiles.active=${SPRING_PROFILES_ACTIVE}" ]
