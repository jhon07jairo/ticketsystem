-- Clear existing data
DELETE FROM Tickets;
DELETE FROM Estados;

-- Reset identity columns
DBCC CHECKIDENT ('Tickets', RESEED, 0);
DBCC CHECKIDENT ('Estados', RESEED, 0);

-- Insert test estados
INSERT INTO Estados (nombre) VALUES 
    ('Nuevo'),
    ('En Progreso'),
    ('En Espera'),
    ('Resuelto'),
    ('Cerrado');

-- Insert test tickets
INSERT INTO Tickets (titulo, descripcion, estadoId, fechaCreacion, fechaActualizacion, fechaVencimiento, comentario)
VALUES 
    ('Test Ticket 1', 'Description for test ticket 1', 1, GETDATE(), NULL, DATEADD(DAY, 7, GETDATE()), NULL),
    ('Test Ticket 2', 'Description for test ticket 2', 2, DATEADD(DAY, -40, GETDATE()), GETDATE(), DATEADD(DAY, -33, GETDATE()), 'In progress'),
    ('Test Ticket 3', 'Description for test ticket 3', 3, DATEADD(DAY, -20, GETDATE()), GETDATE(), DATEADD(DAY, -13, GETDATE()), 'Waiting for response');

-- Create or update the view for testing
IF EXISTS (SELECT * FROM sys.views WHERE name = 'TicketsNoResueltosLog')
    DROP VIEW TicketsNoResueltosLog;

CREATE VIEW TicketsNoResueltosLog AS
SELECT *
FROM Tickets
WHERE estadoId != 4 -- Not resolved
AND DATEDIFF(DAY, fechaCreacion, GETDATE()) > 30;