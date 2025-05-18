# Running the Ticket System Application Without Docker

This guide provides instructions on how to run the Ticket System application without Docker, using a local SQL Server instance with Windows authentication.

## Prerequisites

1. **Java 17**: Make sure you have Java 17 installed on your system.
2. **Maven**: Make sure you have Maven installed on your system.
3. **SQL Server**: Make sure you have SQL Server installed locally on your system.

## Database Setup

1. Open SQL Server Management Studio (SSMS) and connect to your local SQL Server instance.
2. Create a new database named `TicketDB`:
   ```sql
   CREATE DATABASE TicketDB;
   GO
   ```
3. Create the Tickets table:
   ```sql
   USE TicketDB;
   GO
   
   CREATE TABLE Tickets (
       Id BIGINT IDENTITY(1,1) PRIMARY KEY,
       Titulo NVARCHAR(255) NOT NULL,
       Descripcion NVARCHAR(MAX),
       Estado NVARCHAR(50) NOT NULL,
       FechaCreacion DATETIME2 NOT NULL,
       FechaActualizacion DATETIME2,
       FechaVencimiento DATETIME2,
       Comentario NVARCHAR(MAX)
   );
   GO
   ```

## Application Configuration

The application is already configured to use Windows authentication to connect to a local SQL Server instance. The configuration is in the `application.properties` file:

```properties
spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=TicketDB;integratedSecurity=true;encrypt=false;trustServerCertificate=true
spring.datasource.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver
```

## Running the Application

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

- `GET /api/tickets`: Get all tickets
- `GET /api/tickets/{id}`: Get a ticket by ID
- `POST /api/tickets`: Create a new ticket
- `PUT /api/tickets/{id}/estado`: Update the status of a ticket
- `PUT /api/tickets/{id}/resolver`: Mark a ticket as resolved

## Troubleshooting

### Windows Authentication Issues

If you encounter issues with Windows authentication, make sure:

1. The SQL Server instance is configured to allow Windows authentication.
2. The Windows user running the application has the necessary permissions on the SQL Server instance and the `TicketDB` database.
3. The SQL Server JDBC driver is properly loaded. The driver is included as a dependency in the project's pom.xml.

### Connection Issues

If you encounter connection issues:

1. Verify that the SQL Server instance is running on the default port (1433).
2. If SQL Server is running on a different port, update the `spring.datasource.url` property in the `application.properties` file.
3. Make sure the firewall is not blocking connections to SQL Server.