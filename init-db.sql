-- Create database if it doesn't exist
IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = 'TicketDB')
BEGIN
    CREATE DATABASE TicketDB;
END
GO

USE TicketDB;
GO

-- Create Estados table
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'Estados')
BEGIN
    CREATE TABLE Estados (
        id INT IDENTITY(1,1) PRIMARY KEY,
        nombre NVARCHAR(50) NOT NULL
    );
    
    -- Insert default estados
    INSERT INTO Estados (nombre) VALUES 
        ('Nuevo'),
        ('En Progreso'),
        ('En Espera'),
        ('Resuelto'),
        ('Cerrado');
END
GO

-- Create Tickets table
IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'Tickets')
BEGIN
    CREATE TABLE Tickets (
        id BIGINT IDENTITY(1,1) PRIMARY KEY,
        titulo NVARCHAR(255) NOT NULL,
        descripcion NVARCHAR(MAX),
        estadoId INT DEFAULT 1,
        fechaCreacion DATETIME2 DEFAULT GETDATE(),
        fechaActualizacion DATETIME2,
        fechaVencimiento DATETIME2,
        comentario NVARCHAR(MAX),
        CONSTRAINT FK_Tickets_Estados FOREIGN KEY (estadoId) REFERENCES Estados(id)
    );
END
GO

-- Create view for tickets not resolved in 30 days
IF NOT EXISTS (SELECT * FROM sys.views WHERE name = 'TicketsNoResueltosLog')
BEGIN
    EXEC('
    CREATE VIEW TicketsNoResueltosLog AS
    SELECT *
    FROM Tickets
    WHERE estadoId != 4 -- Not resolved
    AND DATEDIFF(DAY, fechaCreacion, GETDATE()) > 30
    ');
END
GO

-- Insert sample data
IF NOT EXISTS (SELECT TOP 1 * FROM Tickets)
BEGIN
    INSERT INTO Tickets (titulo, descripcion)
    VALUES 
        ('Error en login', 'Los usuarios no pueden iniciar sesión'),
        ('Actualización de sistema', 'Planificar actualización de sistema'),
        ('Problema de rendimiento', 'La aplicación se ejecuta lentamente');
END
GO