# uttar-pradesh-tourism-infrastructure-monitoring-system-217397-217406

## Backend Auth (JWT + RBAC) Quickstart

- Endpoints:
  - POST /auth/login { "username": "admin", "password": "Admin@123" }
  - POST /auth/refresh { "refreshToken": "<token>" }
- Protected example endpoints:
  - GET /example/admin-only (requires ROLE_ADMIN)
  - GET /example/permission-project-read (requires PROJECT_READ permission or ADMIN)

Default seeded credentials (dev only):
- username: admin
- password: Admin@123

Configure environment variables (see project_backend/.env.example) to set JWT secrets and DB connection. API docs available at /swagger-ui/index.html.

### Running locally

1) Set environment variables or copy project_backend/.env.example to your environment (do not commit .env):
- SPRING_DATASOURCE_URL, SPRING_DATASOURCE_USERNAME, SPRING_DATASOURCE_PASSWORD
- APP_JWT_SECRET (use a strong base64 secret in production)

2) Start the backend:
- From project_backend: mvn spring-boot:run

3) Open API docs:
- http://localhost:8080/swagger-ui/index.html

Use the login endpoint to obtain a Bearer token, then call protected endpoints with:
Authorization: Bearer <accessToken>
