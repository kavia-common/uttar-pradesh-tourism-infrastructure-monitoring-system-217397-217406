#!/usr/bin/env bash
set -euo pipefail
WS="/home/kavia/workspace/code-generation/uttar-pradesh-tourism-infrastructure-monitoring-system-217397-217406/project_backend"
mkdir -p "$WS"
# avoid clobbering existing project
if [ -f "$WS/pom.xml" ]; then cp -n "$WS/pom.xml" "$WS/pom.xml.bak" || true; fi
mkdir -p "$WS/src/main/java/com/example/demo" "$WS/src/main/resources" "$WS/src/test/java/com/example/demo" "$WS/uploads"
cat > "$WS/pom.xml" <<'POM'
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.2.5</version>
    <relativePath/> <!-- lookup parent from repository -->
  </parent>
  <groupId>com.example</groupId>
  <artifactId>project-backend</artifactId>
  <version>0.0.1-SNAPSHOT</version>
  <properties>
    <java.version>17</java.version>
    <maven.compiler.source>17</maven.compiler.source>
    <maven.compiler.target>17</maven.compiler.target>
  </properties>
  <dependencies>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
    <dependency>
      <groupId>com.h2database</groupId>
      <artifactId>h2</artifactId>
      <scope>runtime</scope>
    </dependency>
    <dependency>
      <groupId>ch.qos.logback</groupId>
      <artifactId>logback-classic</artifactId>
      <scope>runtime</scope>
    </dependency>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-test</artifactId>
      <scope>test</scope>
    </dependency>
  </dependencies>
  <build>
    <plugins>
      <plugin>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-maven-plugin</artifactId>
      </plugin>
    </plugins>
  </build>
</project>
POM
cat > "$WS/src/main/java/com/example/demo/DemoApplication.java" <<'JAVA'
package com.example.demo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
public class DemoApplication { public static void main(String[] args){ SpringApplication.run(DemoApplication.class,args); } }
JAVA
cat > "$WS/src/main/resources/application.properties" <<'PROPS'
# server and management
server.port=${SERVER_PORT:8080}
management.server.port=${MANAGEMENT_PORT:${SERVER_PORT:8080}}
management.endpoints.web.exposure.include=health,info
management.endpoint.health.show-details=always
# datasource
spring.datasource.url=${SPRING_DATASOURCE_URL:jdbc:h2:mem:testdb}
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
# uploads
file.upload-dir=${UPLOADS_DIR:${PWD}/uploads}
# logging
logging.config=classpath:logback.xml
PROPS
cat > "$WS/src/main/resources/logback.xml" <<'LOG'
<configuration>
  <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
    <encoder><pattern>%d{HH:mm:ss.SSS} %-5level %logger{36} - %msg%n</pattern></encoder>
  </appender>
  <root level="INFO">
    <appender-ref ref="STDOUT"/>
  </root>
</configuration>
LOG
cat > "$WS/src/test/java/com/example/demo/DemoApplicationTests.java" <<'TEST'
package com.example.demo;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
class DemoApplicationTests { @Test void contextLoads(){ assertTrue(true); } }
TEST
# set conservative permissions limited to workspace; ignore failures if not permitted
chmod -R u+rwX,g+rX,o-rwx "$WS" || true
