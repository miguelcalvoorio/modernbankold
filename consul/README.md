# Consul

For this distributed architecture I'm using Consul to support both:

- Service registration & discovery
- Load balancing
- Distributed configuration

In order to simplify development and test, I have initially disable Consul registration of microservices, setting ```enabled:false``` property. It can be enable at runtime using ```-D``` command (i.e. ```java -jar app.jar -Dspring.cloud.consul.enabled=true```)

All microservices have this Consul default configuration:

```
cloud:
    consul:
      enabled: false
      host: localhost
      port: 8500
      discovery:
        instance-id: "${spring.application.name}:${random.int[1,999999]}"
        healthCheckPath: ${server.servlet.context-path}/actuator/health
```
