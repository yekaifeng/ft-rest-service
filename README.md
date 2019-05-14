### 有关该项目
这个项目为spring boot demo项目，装备了一些rest api用于作基本功能测试。
目的是为打包成容器镜像，并示范在openshift平台上进行java应用开发，部署，运维过程。

### 指定启动端口

~~~
    # java -jar target/gs-rest-service-0.1.0.jar --spring.config.location=src/main/resources/application-dev.properties
    # java -jar target/gs-rest-service-0.1.0.jar --server.port=9900
~~~

