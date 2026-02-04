# Fees Collection Service

Spring Boot service for collecting student fees, storing receipts, and generating PDF payment receipts. It integrates with a student-service to validate student details and provides a simple REST API for payment collection and receipt download.

## Features
- Collect fees and persist receipts in an H2 in-memory database.
- Validate student details via an external student-service.
- Generate PDF receipts from an HTML template.
- Circuit breaker protection around student-service calls.
- Swagger UI for API exploration.

## Tech Stack
- Java 17, Spring Boot 3.2.x
- Spring Web, Spring Data JPA, Validation
- H2 Database
- openhtmltopdf (PDF generation)
- Resilience4j Circuit Breaker
- Springdoc OpenAPI

## Requirements
- Java 17+
- Maven (or use the included Maven wrapper)

## Running the App
```bash
./mvnw spring-boot:run
```

On Windows:
```bat
mvnw.cmd spring-boot:run
```

## Configuration
The defaults live in `src/main/resources/application.properties`.

Key properties:
- `student_service_url` (default: `http://localhost:8082`)
- H2 console: `/h2-console`
- Swagger UI: `/swagger-ui.html`

## API Endpoints
Base path: `/receipts`

### Collect Fee
`POST /receipts/collectFee`

Request body:
```json
{
  "amount": 1200.0,
  "paymentDate": "2026-02-04",
  "paymentMode": "CARD",
  "email": "parent@example.com",
  "studentId": 123,
  "parentName": "Alex Johnson",
  "cardNumber": "1234"
}
```

Example:
```bash
curl -X POST http://localhost:8080/receipts/collectFee ^
  -H "Content-Type: application/json" ^
  -d "{\"amount\":1200.0,\"paymentDate\":\"2026-02-04\",\"paymentMode\":\"CARD\",\"email\":\"parent@example.com\",\"studentId\":123,\"parentName\":\"Alex Johnson\",\"cardNumber\":\"1234\"}"
```

### Download Receipt (PDF)
`GET /receipts/{studentId}/download`

Example:
```bash
curl -o receipt.pdf http://localhost:8080/receipts/123/download
```

## H2 Console
H2 is configured as an in-memory database.
- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:feesdb`
- User: `sa`
- Password: (empty)

## Notes
- The service expects the student-service to expose `GET /students/{studentId}`.
- If fees were already paid for a student, the API returns HTTP 409.
- The receipt PDF is generated from `src/main/resources/templates/receipt.html`.

## Tests
```bash
./mvnw test
```

