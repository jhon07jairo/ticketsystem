package com.ticket.system.repository;

import com.ticket.system.model.Ticket;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql(scripts = "/test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class TicketRepositoryTest {

    @Autowired
    private TicketRepository ticketRepository;

    @Test
    public void testCrearTicket() {
        // Arrange
        Ticket ticket = new Ticket();
        ticket.setTitulo("Test Ticket");
        ticket.setDescripcion("This is a test ticket");

        // Act
        int result = ticketRepository.crear(ticket);

        // Assert
        assertEquals(1, result, "Should insert one row");
        
        // Verify the ticket was created
        List<Ticket> tickets = ticketRepository.obtenerTodos();
        assertTrue(tickets.stream().anyMatch(t -> 
            "Test Ticket".equals(t.getTitulo()) && 
            "This is a test ticket".equals(t.getDescripcion())),
            "The created ticket should be in the database");
    }

    @Test
    public void testObtenerTodos() {
        // Act
        List<Ticket> tickets = ticketRepository.obtenerTodos();

        // Assert
        assertNotNull(tickets, "Tickets list should not be null");
        assertFalse(tickets.isEmpty(), "Tickets list should not be empty");
    }

    @Test
    public void testObtenerPorId() {
        // Arrange - Assuming test-data.sql inserts a ticket with ID 1
        Long ticketId = 1L;

        // Act
        List<Ticket> tickets = ticketRepository.obtenerPorId(ticketId);

        // Assert
        assertNotNull(tickets, "Tickets list should not be null");
        assertFalse(tickets.isEmpty(), "Tickets list should not be empty");
        assertEquals(ticketId, tickets.get(0).getId(), "Should return ticket with ID 1");
    }

    @Test
    public void testActualizarEstado() {
        // Arrange - Assuming test-data.sql inserts a ticket with ID 1
        Long ticketId = 1L;
        List<Ticket> tickets = ticketRepository.obtenerPorId(ticketId);
        Ticket ticket = tickets.get(0);
        ticket.setEstadoId(2L); // Change to "En Progreso"

        // Act
        int result = ticketRepository.actualizarEstado(ticket);

        // Assert
        assertEquals(1, result, "Should update one row");
        
        // Verify the state was updated
        List<Ticket> updatedTickets = ticketRepository.obtenerPorId(ticketId);
        assertEquals(2L, updatedTickets.get(0).getEstadoId(), "Estado should be updated to 2");
    }

    @Test
    public void testMarcarComoResuelto() {
        // Arrange - Assuming test-data.sql inserts a ticket with ID 1
        Long ticketId = 1L;
        List<Ticket> tickets = ticketRepository.obtenerPorId(ticketId);
        Ticket ticket = tickets.get(0);
        ticket.setComentario("Resolved test ticket");

        // Act
        int result = ticketRepository.marcarComoResuelto(ticket);

        // Assert
        assertEquals(1, result, "Should update one row");
        
        // Verify the ticket was marked as resolved
        List<Ticket> updatedTickets = ticketRepository.obtenerPorId(ticketId);
        assertEquals(4L, updatedTickets.get(0).getEstadoId(), "Estado should be updated to 4 (Resuelto)");
        assertEquals("Resolved test ticket", updatedTickets.get(0).getComentario(), "Comment should be updated");
    }

    @Test
    public void testObtenerNoResueltos30Dias() {
        // Act
        List<Ticket> tickets = ticketRepository.obtenerNoResueltos30Dias();

        // Assert
        assertNotNull(tickets, "Tickets list should not be null");
        // Further assertions depend on test data
    }
}