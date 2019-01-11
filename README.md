# boot-security
Multi Module Spring Boot Project
* JDK 1.8+
* Gradle 5.0
* Spring Boot 2.1.1.RELEASE
# Database
* MySQL 5+ is required
* Redis is used for session management
# Configuration for browser-demo
* Configure data Source in src/main/resources/application.properties
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://<host>:<port>/<database>?useSSL=false
spring.datasource.username=<username>
spring.datasource.password=<password>
* Configure redis spring session in src/main/resources/application.properties
spring.session.store-type=redis
# Configuration for app-demo
* Configure data Source in src/main/resources/application.properties
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://<host>:<port>/<database>?useSSL=false
spring.datasource.username=<username>
spring.datasource.password=<password>
* Configure redis spring session in src/main/resources/application.properties
spring.session.store-type=redis
