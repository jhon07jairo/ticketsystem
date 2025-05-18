-- Crear base de datos (opcional)
CREATE DATABASE TicketDB;
GO

USE TicketDB;
GO

-- Tabla principal de tickets
CREATE TABLE Tickets (
    Id INT IDENTITY(1,1) PRIMARY KEY,
    Titulo NVARCHAR(255) NOT NULL,
    Descripcion NVARCHAR(MAX) NOT NULL,
    Estado NVARCHAR(50) NOT NULL CHECK (Estado IN ('Abierto', 'En Proceso', 'Cerrado', 'Resuelto')),
    FechaCreacion DATETIME NOT NULL DEFAULT GETDATE(),
    FechaActualizacion DATETIME NULL,
    FechaVencimiento DATETIME NULL,
    Comentario NVARCHAR(MAX) NULL
);
GO

-- Tabla para guardar registro de tickets no resueltos que superan los 30 días
CREATE TABLE TicketsNoResueltosLog (
    Id INT IDENTITY(1,1) PRIMARY KEY,
    TicketId INT NOT NULL,
    FechaRegistro DATETIME NOT NULL DEFAULT GETDATE(),
    FOREIGN KEY (TicketId) REFERENCES Tickets(Id)
);
GO

-- Procedimiento almacenado para guardar tickets no resueltos mayores a 30 días
CREATE OR ALTER PROCEDURE RegistrarTicketsNoResueltos
AS
BEGIN
    INSERT INTO TicketsNoResueltosLog (TicketId)
    SELECT Id
    FROM Tickets
    WHERE EstadoId != 4  -- Usar EstadoId diferente de 4
      AND DATEDIFF(DAY, FechaCreacion, GETDATE()) > 30;
END;
GO

-- Insertar tickets de prueba con la nueva estructura
INSERT INTO Tickets (Titulo, Descripcion)
VALUES
('Error en login', 'El sistema no permite iniciar sesión con credenciales válidas.'),
('Fallo al generar reporte', 'El reporte mensual lanza error 500.'),
('Solicitud de nueva funcionalidad', 'Agregar botón de exportar a Excel.'),
('Problema con notificaciones', 'Las notificaciones por correo no están funcionando.'),
('Cambio de contraseña obligatorio', 'Se solicita cambiar contraseña cada 90 días.'),
('Demora al cargar el dashboard', 'El dashboard se demora más de 10 segundos en cargar.');
GO


-- Ejecutar el SP para registrar los tickets no resueltos con más de 30 días
EXEC RegistrarTicketsNoResueltos;

-- Ver tickets registrados como no resueltos
SELECT * FROM TicketsNoResueltosLog;
SELECT * FROM Tickets;
--delete from Tickets
--delete from TicketsNoResueltosLog
ALTER TABLE Tickets
ADD CONSTRAINT DF_Tickets_Estado DEFAULT 'Abierto' FOR Estado;
ALTER TABLE Tickets
ADD CONSTRAINT DF_Tickets_FechaCreacion DEFAULT GETDATE() FOR FechaCreacion;

-- Tabla de Estados
CREATE TABLE Estados (
    Id INT PRIMARY KEY, -- Puedes usar un IDENTITY si prefieres que SQL Server gestione los IDs
    Nombre NVARCHAR(50) NOT NULL UNIQUE
);
GO

-- Insertar algunos estados por defecto
INSERT INTO Estados (Id, Nombre) VALUES
(1, 'Abierto'),
(2, 'En Proceso'),
(3, 'Cerrado'),
(4, 'Resuelto');
GO


ALTER TABLE Tickets
ADD EstadoId INT NOT NULL DEFAULT 1; -- Agregamos la columna EstadoId con valor por defecto 'Abierto' (Id 1)

ALTER TABLE Tickets
ADD CONSTRAINT FK_Tickets_Estados FOREIGN KEY (EstadoId) REFERENCES Estados(Id);

ALTER TABLE Tickets
DROP COLUMN Estado; -- Eliminamos la columna Estado anterior
GO

-- Modificar el valor por defecto de FechaVencimiento
ALTER TABLE Tickets
ADD CONSTRAINT DF_FechaVencimiento DEFAULT DATEADD(week, 1, GETDATE()) FOR FechaVencimiento;
GO

UPDATE Tickets SET estadoId = 4, comentario = 'Comentario resuelto' , fechaActualizacion = GETDATE() WHERE id = 52;