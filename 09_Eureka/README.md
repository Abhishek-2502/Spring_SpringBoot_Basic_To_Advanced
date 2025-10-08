# 📘 Microservices with Eureka

## 1. Microservices and the Problem of Service Location

* In a **monolithic application**, all modules run in one process → no service discovery needed.
* In **microservices**, each service (like Order, Payment, Inventory) runs independently, often on **different servers/ports**.
* Problems if we don’t use discovery:

  * Services change IPs/ports when scaled.
  * Hardcoding URLs leads to failure in dynamic environments (like Docker, Kubernetes, or cloud).

👉 Solution: **Service Discovery System** (Eureka).

---

## 2. Service Discovery

Service Discovery is the mechanism that allows services to **find and communicate** with each other dynamically.
There are two types:

1. **Client-Side Discovery**

   * The client asks the **Service Registry** for the location of a service.
   * Example: Eureka + Feign.

2. **Server-Side Discovery**

   * A **Load Balancer** queries the Service Registry and routes requests.
   * Example: AWS Elastic Load Balancer with ECS.

👉 Eureka is used in **Client-Side Discovery**.

---

## 3. What is Eureka?

* Eureka is part of **Netflix OSS** (later integrated into Spring Cloud).
* Provides **Service Registry + Discovery**.
* Services (clients) register themselves and look up other services.

### Components:

1. **Eureka Server (Naming Server)** → Registry of all services.
2. **Eureka Client** → Any microservice that registers with Eureka.

---

## 4. Eureka Server (Naming Server)

### Role

* Acts like a **phone directory** of services.
* Stores:

  * Service Name
  * Instance ID
  * Host and Port
  * Status (UP/DOWN)

### Important Features

* **Self-Preservation Mode** → prevents mass removal of services during temporary network failures.
* **Heartbeat mechanism** → clients send pings every 30s by default.
* **REST Endpoints** → server exposes REST APIs to query registered services.

---

### 🛠️ Implementation

#### Dependency

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
</dependency>
```

#### Main Class

```java
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }
}
```

#### application.properties

```properties
# Eureka Server on port 8761
server.port=8761

# Don’t register Eureka Server itself
eureka.client.register-with-eureka=false
eureka.client.fetch-registry=false

spring.application.name=EUREKA-SERVER
```

👉 Access Dashboard: `http://localhost:8761`

---

## 5. Eureka Client (Service Registration)

### Role

* Any microservice (Order, Payment, Inventory) acts as a **client**.
* Registers itself with Eureka Server.
* Keeps sending **heartbeats** to confirm it’s alive.

---

### 🛠️ Implementation

#### Dependency

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```

#### Main Class

```java
@SpringBootApplication
@EnableEurekaClient
public class PaymentServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(PaymentServiceApplication.class, args);
    }
}
```

#### application.properties

```properties
# Port for this service
server.port=8082

# Register this service in Eureka
spring.application.name=PAYMENT-SERVICE

# Eureka Server URL
eureka.client.service-url.defaultZone=http://localhost:8761/eureka/

# (Optional) How often to renew registration
eureka.instance.lease-renewal-interval-in-seconds=30
eureka.instance.lease-expiration-duration-in-seconds=90
```

---

## 6. Naming Server

* Eureka Server doubles as a **Naming Server**.
* Instead of IP + port, you use **service names**.

Example:

* Without Eureka → `http://localhost:8082/payment/status`
* With Eureka → `http://PAYMENT-SERVICE/payment/status`

👉 Eureka resolves `PAYMENT-SERVICE` to the correct instance automatically.

![Service Discovery Architecture](<Eureka_Architecture.png>)

---

## 7. Feign Client

### Why Feign?

* Traditional approach (`RestTemplate`) requires manual URL construction.
* Feign removes boilerplate by allowing **interface-driven REST clients**.
* Integrates directly with Eureka → service name is enough.

---

### 🛠️ Implementation

#### Dependency

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```

#### Main Class (Order Service)

```java
@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient
public class OrderServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }
}
```

#### application.properties (Order Service)

```properties
server.port=8081
spring.application.name=ORDER-SERVICE

# Eureka Server location
eureka.client.service-url.defaultZone=http://localhost:8761/eureka/
```

#### Feign Client Interface

```java
@FeignClient(name = "PAYMENT-SERVICE")
public interface PaymentClient {
    @GetMapping("/payment/status")
    String getPaymentStatus();
}
```

#### Using Feign Client

```java
@Service
public class OrderService {
    private final PaymentClient paymentClient;

    public OrderService(PaymentClient paymentClient) {
        this.paymentClient = paymentClient;
    }

    public String placeOrder() {
        String status = paymentClient.getPaymentStatus();
        return "Order placed successfully. Payment Status: " + status;
    }
}
```

---

## 8. Lifecycle of Service Discovery with Eureka

1. **Eureka Server** starts at `8761`.
2. **Payment Service** (port 8082) registers as `PAYMENT-SERVICE`.
3. **Order Service** (port 8081) registers as `ORDER-SERVICE`.
4. Order Service calls `paymentClient.getPaymentStatus()`.
5. Feign asks Eureka for `PAYMENT-SERVICE`.
6. Eureka returns available instance (e.g., `localhost:8082`).
7. Feign sends REST request automatically.
8. Response flows back to Order Service.

---

## 9. Advantages of Eureka + Feign

* **Dynamic Discovery**: No hardcoded URLs.
* **Load Balancing**: Multiple instances supported (via Ribbon integration).
* **Fault Tolerance**: If one instance fails, others serve traffic.
* **Scalability**: New service instances auto-register.
* **Declarative Syntax**: Feign reduces boilerplate REST calls.
* **Centralized Registry**: Easier monitoring (via dashboard).

---

## 10. Limitations & Alternatives

* **Netflix OSS maintenance stopped** (but Spring Cloud keeps supporting).
* **Not cloud-native** by default → Kubernetes/Consul/Zookeeper more modern.
* No built-in **API Gateway** → must combine with **Spring Cloud Gateway** or Zuul.

---

## 11. API Gateway in Microservices

### Why API Gateway?

* In a **microservices system**, clients (web/mobile) should not directly call each microservice.
* Problems without a gateway:

  * Each client must know multiple service URLs (Order, Payment, Inventory, etc.).
  * Hard to secure and manage.
  * Cross-cutting concerns (auth, logging, rate limiting) get repeated in every service.

👉 **API Gateway** solves this by acting as a **single entry point**.

### Responsibilities of an API Gateway:

* **Request Routing**: Forward requests to appropriate microservice.
* **Load Balancing**: Distribute requests across multiple service instances.
* **Security**: Central place for authentication/authorization.
* **Cross-cutting concerns**: Logging, monitoring, rate-limiting.

---

## 12. Spring Cloud Gateway (API Gateway)

### Features

* Built on **Spring WebFlux** (non-blocking, reactive).
* Works seamlessly with **Eureka Server** (service discovery).
* Supports advanced routing with filters (authentication, logging, retries, etc.).

---

### 🛠️ Implementation

#### Dependency

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-gateway</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```

#### Main Class

```java
@SpringBootApplication
@EnableEurekaClient
public class ApiGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }
}
```

#### application.properties

```properties
# Gateway runs on port 8080
server.port=8080
spring.application.name=API-GATEWAY

# Register with Eureka
eureka.client.service-url.defaultZone=http://localhost:8761/eureka/

# Enable service discovery locator
spring.cloud.gateway.discovery.locator.enabled=true
spring.cloud.gateway.discovery.locator.lower-case-service-id=true

# Example: Explicit route mapping
spring.cloud.gateway.routes[0].id=order-service
spring.cloud.gateway.routes[0].uri=lb://ORDER-SERVICE
spring.cloud.gateway.routes[0].predicates[0]=Path=/order/**

spring.cloud.gateway.routes[1].id=payment-service
spring.cloud.gateway.routes[1].uri=lb://PAYMENT-SERVICE
spring.cloud.gateway.routes[1].predicates[0]=Path=/payment/**
```

---

### How It Works

* API Gateway is registered with Eureka (`API-GATEWAY`).
* `lb://SERVICE-NAME` → tells Gateway to use **LoadBalancer + Eureka** for routing.
* Client calls:

  ```
  http://localhost:8080/order/place
  http://localhost:8080/payment/status
  ```
* Gateway:

  * Looks up `ORDER-SERVICE` or `PAYMENT-SERVICE` in Eureka.
  * Routes request to available instance.
  * Applies filters (auth, logging, etc.) before forwarding.

---

## 13. API Gateway + Eureka + Feign (Complete Flow)

1. **Client** → sends request to **API Gateway (8080)**.
2. Gateway → asks **Eureka Server (8761)** for service location.
3. Eureka → returns available instances (e.g., `ORDER-SERVICE@8081`).
4. Gateway → forwards request to the selected instance.
5. Order Service → internally uses **Feign Client** to call `PAYMENT-SERVICE`.
6. Response flows back through Gateway to Client.

---

## 14. Advantages of API Gateway

* **Single Entry Point** → Clients don’t need to know multiple service URLs.
* **Load Balancing** → Automatically handles multiple service instances.
* **Centralized Security** → Apply authentication once at gateway.
* **Scalability** → New services can be added without client-side changes.
* **Resilience** → Supports retries, fallbacks, circuit breakers.

---

✅ **Interview Points**

* **Q: Why do we need Eureka?**
  
  To dynamically register and discover services, avoiding hardcoded IPs/ports.

* **Q: Difference between Eureka Server and Eureka Client?**
  
  Server → central registry; Client → microservice that registers itself.

* **Q: What is a Naming Server?**
  
  Eureka Server, enabling services to be discovered by logical name.

* **Q: How does Feign simplify service communication?**
  
  By using an interface-based declarative approach instead of boilerplate REST code.

* **Q: What happens if Eureka Server goes down?**
  
  Clients keep last known registry cached → continue communicating temporarily.

* **Q: Why do we need an API Gateway in microservices?**

  * Acts as a **single entry point** for all clients.
  * Handles **routing** of requests to respective microservices.
  * Provides **cross-cutting concerns** like authentication, authorization, logging, monitoring, and rate limiting.
  * Reduces coupling between clients and services.

* **Q: How does API Gateway work with Eureka?**
  
  The API Gateway itself is a **Eureka Client**.

  * It registers with Eureka.
  * It uses service names (from the registry) instead of hardcoded URLs to route traffic.
  * Example: Client calls `/order/**` → Gateway looks up `ORDER-SERVICE` in Eureka and forwards the request to the correct instance.

* **Q: Difference between API Gateway and Eureka?**

  * **Eureka** → Service Registry (directory of services).
  * **API Gateway** → Traffic controller (routes requests to services, applies filters/security).
    Together they provide discovery + centralized access.

* **Q: Can Feign work without API Gateway?**
  
  Yes, Feign can directly call services via Eureka. But with API Gateway, you gain **security, monitoring, load balancing, and unified access**.

* **Q: Which is better: API Gateway or direct Feign calls?**

  * **Direct Feign** → Better for internal service-to-service calls.
  * **API Gateway** → Better for external client-to-service calls (security, throttling, monitoring).

---


✅ **Final Summary**

* **Eureka Server** → Service Registry (Naming Server).
* **Eureka Client** → Services register and discover each other.
* **Naming Server** → Maps logical service names to actual instances.
* **Feign Client** → Simplifies inter-service communication.
* **API Gateway** → Provides a single entry point, routing, security, and load balancing.

