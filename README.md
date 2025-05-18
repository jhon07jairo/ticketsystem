# Ticket System Application

This guide provides instructions on how to run the Ticket System application both with and without Docker.

## Running with Docker (Recommended)

### Prerequisites

1. **Docker**: Make sure you have Docker and Docker Compose installed on your system.

### Running the Application

1. Clone the repository:
   ```
   git clone https://github.com/jhon07jairo/ticketsystem.git
   cd ticketsystem
   ```

2. Build and start the containers:
   ```
   docker-compose up -d
   ```

3. The application will be available at http://localhost:9000

### Stopping the Application

To stop the application:
```
docker-compose down
```

To stop the application and remove all data:
```
docker-compose down -v
```

## Running without Docker

### Prerequisites

1. **Java 17**: Make sure you have Java 17 installed on your system.
2. **Maven**: Make sure you have Maven installed on your system.
3. **SQL Server**: Make sure you have SQL Server installed locally on your system.

### Database Setup

1. Open SQL Server Management Studio (SSMS) and connect to your local SQL Server instance.
2. Create a new database named `TicketDB`:
   ```sql
   CREATE DATABASE TicketDB;
   GO
   ```
3. Run the initialization script from `init-db.sql` to create the necessary tables and views.

### Application Configuration

The application is configured to connect to a SQL Server instance. The configuration is in the `application.properties` file:

```properties
spring.datasource.url=jdbc:sqlserver://localhost\\SQLEXPRESS:1433;databaseName=TicketDB;encrypt=false;trustServerCertificate=true
spring.datasource.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver
spring.datasource.username=sa
spring.datasource.password=12345
```

Update these values as needed for your environment.

### Running the Application

1. Open a command prompt or PowerShell window.
2. Navigate to the project directory:
   ```
   cd path\to\ticket-system
   ```
3. Build the application:
   ```
   mvn clean package
   ```
4. Run the application:
   ```
   java -jar target\ticket-system-0.0.1-SNAPSHOT.jar
   ```
5. The application will start and be available at http://localhost:9000

## API Endpoints

The application provides the following API endpoints:

- `GET /api/tickets/list`: Get all tickets
- `POST /api/tickets/crear`: Create a new ticket
- `POST /api/tickets/actualizar-estado`: Update the status of a ticket
- `POST /api/tickets/marcar-resuelto`: Mark a ticket as resolved
- `GET /api/tickets/no-resueltos-30-dias`: Get tickets not resolved in 30 days

## Running Tests

To run the tests:
```
mvn test
```

## Project Structure

- `src/main/java/com/ticket/system`: Java source code
  - `controller`: REST controllers
  - `model`: Data models
  - `repository`: Data access layer
  - `service`: Business logic
- `src/test`: Test code
- `Dockerfile`: Docker configuration for the application
- `docker-compose.yml`: Docker Compose configuration
- `init-db.sql`: Database initialization script
