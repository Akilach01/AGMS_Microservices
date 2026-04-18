# 🌱 Automated Greenhouse Management System (AGMS)

**Microservice-Based Application — ITS 2018 Final Examination**

---

## 📌 Project Overview

The **Automated Greenhouse Management System (AGMS)** is a cloud-native, microservices-based platform designed to automate greenhouse operations using real-time environmental data.

The system integrates with an **external IoT API** to retrieve live telemetry data (temperature and humidity), processes it through a rule engine, and triggers automated actions to maintain optimal growing conditions.

---

## 🚀 Key Features

- Microservices architecture using Spring Boot & Spring Cloud  
- Service discovery using Eureka Server  
- Centralized configuration with Spring Cloud Config  
- API Gateway with JWT-based authentication  
- Real-time IoT data integration  
- Automated rule-based decision engine  
- Crop lifecycle management system  

---

## 🏗️ System Architecture

![System Architecture](docs/SystemArchitecture.png)

---

## 🛠️ Technology Stack

| Technology       | Purpose                        |
|-----------------|------------------------------|
| Spring Boot      | Microservices development      |
| Spring Cloud     | Distributed system support     |
| Eureka Server    | Service discovery              |
| Config Server    | Centralized configuration      |
| API Gateway      | Routing & security             |
| OpenFeign        | Inter-service communication    |
| RestTemplate     | Internal HTTP communication    |
| MySQL            | Database                       |
| JWT              | Authentication                 |
| External IoT API | Live telemetry data            |

---

## 📋 Prerequisites

- Java 17+  
- Maven 3.9+  
- MySQL (XAMPP or standalone)  
- Postman  

---

## 🗄️ Database Setup

1. Start MySQL (XAMPP or standalone)
2. Default credentials:

```
Username: root
Password: (empty)
```

3. Database will be auto-created:

```
AGMS_Db
```

---

## ⚙️ Sample Configuration (zone-service.yml)

```yaml
server:
  port: 8081

spring:
  application:
    name: zone-service
  config:
    import: optional:configserver:http://localhost:8888
  datasource:
    url: jdbc:mysql://localhost:3306/AGMS_Db?createDatabaseIfNotExist=true
    username: root
    password: your_password
    driver-class-name: com.mysql.cj.jdbc.Driver
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/
```

---

## ⚙️ Centralized Configuration

- All microservices act as **Config Clients**
- Configuration is managed through a **central Git repository**
- Services fetch configurations at startup
- Supports dynamic updates without rebuilding services

---

## 🚀 Running the Application

### 🔹 Recommended Order

Start services in the following order:

1. Config Server  
2. Eureka Server  
3. API Gateway  
4. Zone Service  
5. Sensor Service  
6. Automation Service  
7. Crop Service  

---

### 🔹 Verify System

Open:

```
http://localhost:8761
```

Expected services:

| Service            | Port | Status |
|------------------|------|--------|
| CONFIG-SERVER     | 8888 | UP     |
| API-GATEWAY       | 8080 | UP     |
| ZONE-SERVICE      | 8081 | UP     |
| SENSOR-SERVICE    | 8082 | UP     |
| AUTOMATION-SERVICE| 8083 | UP     |
| CROP-SERVICE      | 8084 | UP     |

---

## 🔄 System Workflow

1. Zone Service creates zones and registers IoT devices  
2. Sensor Service fetches live data periodically  
3. Data is sent to Automation Service  
4. Automation Service retrieves thresholds from Zone Service  
5. Rules are applied:

   - Temp > max → TURN_FAN_ON  
   - Temp < min → TURN_HEATER_ON  

6. Logs are stored and accessible via API  

---

## 🌡️ Sensor Data Flow

Sensor Service fetches data every 10 seconds from the external IoT API and sends it to the Automation Service.

### Example Payload

```json
{
  "zoneId": "Zone-A",
  "temperature": 24.5,
  "humidity": 60
}
```

---

## 🔗 Inter-Service Communication

- OpenFeign is used for service-to-service communication  
- Example: Automation → Zone Service  
- RestTemplate used for additional HTTP calls  

---

## 🌐 API Endpoints (via Gateway - Port 8080)

### Zone Service
- POST `/api/zones`
- GET `/api/zones/{id}`
- PUT `/api/zones/{id}`
- DELETE `/api/zones/{id}`

### Sensor Service
- GET `/api/sensors/latest`

### Automation Service
- POST `/api/automation/process`
- GET `/api/automation/logs`

### Crop Service
- POST `/api/crops`
- PUT `/api/crops/{id}/status`
- GET `/api/crops`

---

## 🌱 Crop Lifecycle

Valid transitions:
- SEEDLING → VEGETATIVE  
- VEGETATIVE → HARVESTED  

Invalid transitions are rejected.

---

## 🔐 JWT Authentication

1. User logs in via `/auth`
2. Receives JWT token  
3. Token sent in request headers  
4. API Gateway validates token  
5. Unauthorized requests are blocked  

---

## 🌍 External IoT API

```
http://104.211.95.241:8080/api
```

- Used by Sensor Service for telemetry  
- Used by Zone Service for device registration  

---

## 🧪 Testing

1. Open Postman  
2. Import collection:
```
postman/agms-postman-collection.json
```
3. Test all endpoints via Gateway  

---

## 📁 Project Structure

```
agms-microservices/
├── config-server/
├── eureka-server/
├── api-gateway/
├── zone-service/
├── sensor-service/
├── automation-service/
├── crop-service/
├── docs/
├── postman/
└── README.md
```

---

## ✅ Final Status

- Microservices are fully functional  
- Services registered successfully in Eureka  
- External API integration working  
- JWT security implemented  
- End-to-end workflow verified  
