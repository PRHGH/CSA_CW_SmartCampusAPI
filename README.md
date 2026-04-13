# Smart Campus API (JAX-RS with Jersey)

## Overview

This project is a RESTful API for managing a Smart Campus system. It allows clients to manage:

* Rooms
* Sensors assigned to rooms
* Sensor readings (historical data)

The API is built using **JAX-RS (Jersey implementation)** and deployed on **Apache Tomcat**, with **in-memory storage** (no database).

---

## Report Answers

### 1. Resource Lifecycle

JAX-RS resources are typically request-scoped. A new instance is created per request, so shared data is stored in a static `DataStore`.

---

### 2. HATEOAS and Discovery

The root endpoint (`/api/v1`) provides links to main resources, allowing clients to discover available endpoints dynamically.

---

### 3. IDs vs Full Objects

Returning only IDs reduces payload size, while full objects improve usability. This API uses full objects for clarity.

---

### 4. DELETE Idempotency

DELETE is idempotent because multiple calls result in the same final state (resource removed), even if subsequent calls return 404.

---

### 5. @Consumes(APPLICATION_JSON)

Ensures endpoints only accept JSON. Other content types may result in errors.

---

### 6. Query vs Path Parameters

Query parameters (`?type=...`) are used for filtering collections, while paths represent specific resources.

---

### 7. Sub-resource Locator Benefits

Improves modularity by separating nested logic (`SensorReadingResource`) from main resource (`SensorResource`).

---

### 8. Why 422 Instead of 404

The endpoint exists and request is valid, but a referenced resource inside the payload is missing → `422` is more appropriate.

---

### 9. Stack Trace Risks

Exposing stack traces reveals internal implementation details and security risks. A global exception mapper prevents this.

---

### 10. Logging with Filters

Filters centralize logging logic, avoiding duplication and improving maintainability.

---

## Technologies Used

* Java (JDK 8+)
* JAX-RS (Jersey)
* Maven (WAR packaging)
* Apache Tomcat
* JSON (application/json)

---

## How to Build and Run

### 1. Clone the repository

```bash
git clone https://github.com/PRHGH/CSA_CW_SmartCampusAPI.git
```

### 2. Open in NetBeans

* Open Apache NetBeans
* File → Open Project → Select this project

### 3. Configure Tomcat

* Ensure Apache Tomcat is added in NetBeans
* Set it as the project server

### 4. Build and Run

* Right-click project → Run
* OR:

```bash
mvn clean package
```

### 5. Base URL

```
http://localhost:8080/CSA_CW_SmartCampusAPI/api/v1
```

---

## API Endpoints

### Discovery

```http
GET /api/v1
```

Returns API metadata and resource links.

---

### Rooms

```http
GET    /rooms
POST   /rooms
GET    /rooms/{id}
DELETE /rooms/{id}
```

---

### Sensors

```http
GET  /sensors
GET  /sensors?type={type}
POST /sensors
```

---

### Sensor Readings (Nested Resource)

```http
GET  /sensors/{id}/readings
POST /sensors/{id}/readings
```

---

## Sample curl Commands

### Get API info

```bash
curl -X GET http://localhost:8080/CSA_CW_SmartCampusAPI/api/v1
```

---

### Create Room

```bash
curl -X POST http://localhost:8080/CSA_CW_SmartCampusAPI/api/v1/rooms \
-H "Content-Type: application/json" \
-d '{"id":"LAB-101","name":"Computer Lab","capacity":40}'
```

---

### Get All Rooms

```bash
curl -X GET http://localhost:8080/CSA_CW_SmartCampusAPI/api/v1/rooms
```

---

### Create Sensor

```bash
curl -X POST http://localhost:8080/CSA_CW_SmartCampusAPI/api/v1/sensors \
-H "Content-Type: application/json" \
-d '{"id":"TEMP-001","type":"Temperature","status":"ACTIVE","currentValue":26.5,"roomId":"LAB-101"}'
```

---

### Filter Sensors

```bash
curl -X GET "http://localhost:8080/CSA_CW_SmartCampusAPI/api/v1/sensors?type=Temperature"
```

---

### Add Reading

```bash
curl -X POST http://localhost:8080/CSA_CW_SmartCampusAPI/api/v1/sensors/TEMP-001/readings \
-H "Content-Type: application/json" \
-d '{"id":"READ-001","timestamp":1712920000000,"value":27.8}'
```

---

### Delete Room

```bash
curl -X DELETE http://localhost:8080/CSA_CW_SmartCampusAPI/api/v1/rooms/LAB-101
```

---

## Error Handling

The API uses custom exceptions and mappers:

| Scenario                         | Status Code               |
| -------------------------------- | ------------------------- |
| Room has sensors (cannot delete) | 409 Conflict              |
| Duplicate resource ID            | 409 Conflict              |
| Missing linked room in sensor    | 422 Unprocessable Entity  |
| Sensor in MAINTENANCE            | 403 Forbidden             |
| Resource not found               | 404 Not Found             |
| Unexpected error                 | 500 Internal Server Error |

All errors return JSON responses with:

```json
{
  "error": "...",
  "message": "...",
  "status": ...
}
```

---

##  Design Highlights

* **Sub-resource locator pattern** used for nested readings
* **Separation of concerns** using services, resources, and mappers
* **Centralized logging** using request/response filters
* **Consistent error handling** via ExceptionMapper classes

---
