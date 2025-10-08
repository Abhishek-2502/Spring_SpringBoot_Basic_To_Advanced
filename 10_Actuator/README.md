
# Spring Boot Actuator
## **Overview**

Spring Boot Actuator is a **production-ready feature** that helps monitor and manage Spring Boot applications.
It provides **built-in endpoints** to monitor application health, metrics, environment, and more.

Adding the dependency:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

enables these features in your Spring Boot application.

---

## **Key Features**

| Feature                | Description                                               | Default Endpoint      |
| ---------------------- | --------------------------------------------------------- | --------------------- |
| **Health Checks**      | Checks application status (DB, disk space, custom checks) | `/actuator/health`    |
| **Metrics**            | Provides JVM, memory, GC, HTTP request metrics            | `/actuator/metrics`   |
| **Environment Info**   | Displays properties from application configuration        | `/actuator/env`       |
| **Application Info**   | Shows version, build time, and custom info                | `/actuator/info`      |
| **HTTP Tracing**       | Monitors recent HTTP requests                             | `/actuator/httptrace` |
| **Loggers Management** | View and change logging levels dynamically                | `/actuator/loggers`   |
| **Custom Endpoints**   | Create your own monitoring endpoints                      | `/actuator/<custom>`  |

---

## **Setup and Usage**

1. **Add the dependency** to your `pom.xml` (Maven) or `build.gradle` (Gradle).

2. **Expose endpoints** in `application.properties` or `application.yml`:

```properties
# For Prometheus Monitoring
# Expose necessary actuator endpoints
management.endpoints.web.exposure.include=health,info,prometheus,loggers,metrics,env

# Enable Prometheus endpoint
management.endpoint.prometheus.enabled=true

# Set server port
management.server.port=8080

# Show detailed health info
management.endpoint.health.show-details=always
```

3. **Access endpoints** via browser or API:
   Example: `http://localhost:8080/actuator/health`

4. **Secure endpoints** for production:
   Use Spring Security to restrict access to sensitive endpoints.

---

## **Integration with Prometheus**

Prometheus is a **monitoring and alerting tool** that can scrape and store metrics exposed by Spring Boot Actuator.
You can easily integrate it using **Micrometer**, which acts as a bridge between Actuator and Prometheus.

### **Add Prometheus Dependency**

```xml
<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-registry-prometheus</artifactId>
</dependency>
```

### **If Using Spring Security**

Permit Prometheus to scrape the `/actuator/prometheus` endpoint:

```java
.requestMatchers("/actuator/prometheus").permitAll()
```

### **Access Metrics**

After starting the application, Prometheus metrics are available at:
👉 `http://localhost:8080/actuator/prometheus`

### **Configure Prometheus Server**

Add the following job in your `prometheus.yml` to scrape Spring Boot metrics:

```yaml
scrape_configs:
  - job_name: 'spring-boot-app'
    metrics_path: '/actuator/prometheus'
    static_configs:
      - targets: ['host.docker.internal:8080']
```

---

## **Notes**

* By default, only `health` and `info` endpoints are exposed over HTTP.
* Actuator integrates seamlessly with **Prometheus** for metrics collection.
* Ideal for **observability, debugging, and performance tracking**.
* You can create **custom health indicators** and **custom metrics** as needed.

## Author
 - Abhishek Rajput