# Banking System Loan Microservice

**Microservicio para la gestión integral de préstamos del banco.**

Este servicio se encarga de todo el ciclo de vida de un préstamo personal, desde su solicitud y cálculo de intereses hasta el seguimiento de las cuotas. Permite a los clientes solicitar financiamiento y visualizar su estado de deuda de manera reactiva.

## Descripción del Proyecto

El **Loan Microservice** utiliza un motor reactivo para manejar solicitudes de crédito de forma eficiente, permitiendo integrarse fácilmente con el resto del bancario (Clientes y Cuentas).

### Qué hace esta aplicación
- **Solicitud de Préstamos:** Permite crear nuevos registros de préstamos con montos, tasas y plazos.
- **Cálculo de Deuda:** Gestiona el monto total a pagar incluyendo intereses.
- **Seguimiento por Cliente:** Filtra y agrupa los préstamos por el ID del cliente.
- **Gestión de Cuotas:** (En desarrollo) Punto de entrada para el desglose de cuotas y fechas de pago.
- **Persistencia Reactiva:** Utiliza **R2DBC** para operaciones con base de datos no bloqueantes.

### Tecnologías utilizadas
- **Java 21**
- **Spring Boot 3.x / WebFlux** (Programación Reactiva).
- **Spring Data R2DBC**: Para persistencia en PostgreSQL de alto rendimiento.
- **PostgreSQL**: Motor de base de datos relacional.
- **Lombok**: Para la generación de código boilerplate.
- **Eureka Client & Config Client**.

---

## Cómo instalar y ejecutar el proyecto

### Requisitos previos
1. **ms-config-server** corriendo.
2. **registry-service** corriendo.
3. Base de datos **PostgreSQL** (db_loan) disponible.

### Pasos para ejecución local (Gradle)
1. Navega a la carpeta: `cd ms-loan`
2. Ejecuta:
   ```bash
   ./gradlew bootRun
   ```

### Pasos para ejecución con Docker
```bash
docker-compose up -d ms-loan
```

---

## Cómo utilizar el proyecto

### Endpoints (v1)
- **Crear préstamo:** `POST /api/v1/loans`
- **Buscar por Cliente:** `GET /api/v1/loans/customer/{customerId}`
- **Obtener cuotas:** `GET /api/v1/loans/{loanId}/installments`

