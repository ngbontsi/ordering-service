# Ordering Service - Scaffold

This scaffold contains a starting point for the Ordering microservice.

Features included:
- Spring Boot (REST)
- Flyway migrations
- Lombok
- MapStruct mapper
- Redis (idempotency + caching)
- Kafka event publisher
- Tenant filter (X-Client-ID)
- OpenAPI (springdoc)
- Dockerfile + docker-compose for local dev (Postgres, Redis, Redpanda)
- GitHub Actions CI workflow

Next steps: implement Product Catalog HTTP client or cache lookup, more complete order item snapshotting, payment integration, sagas.
