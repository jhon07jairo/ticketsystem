package com.ticket.system.service;

import com.ticket.system.model.Ticket;
import com.ticket.system.repository.ITicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TicketServiceTest {

    @Mock
    private ITicketRepository ticketRepository;

    @InjectMocks
    private TicketService ticketService;

    private Ticket testTicket;
    private List<Ticket> testTickets;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        // Create test ticket
        testTicket = new Ticket();
        testTicket.setId(1L);
        testTicket.setTitulo("Test Ticket");
        testTicket.setDescripcion("Test Description");
        testTicket.setEstadoId(1L);
        testTicket.setFechaCreacion(LocalDateTime.now());

        // Create test ticket list
        testTickets = Arrays.asList(testTicket);
    }

    @Test
    public void testCrear() {
        // Arrange
        when(ticketRepository.crear(any(Ticket.class))).thenReturn(1);

        // Act
        int result = ticketService.crear(testTicket);

        // Assert
        assertEquals(1, result);
        verify(ticketRepository, times(1)).crear(testTicket);
    }

    @Test
    public void testCrearException() {
        // Arrange
        when(ticketRepository.crear(any(Ticket.class))).thenThrow(new RuntimeException("Test exception"));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            ticketService.crear(testTicket);
        });
        
        assertEquals("Test exception", exception.getMessage());
        verify(ticketRepository, times(1)).crear(testTicket);
    }

    @Test
    public void testObtenerTodos() {
        // Arrange
        when(ticketRepository.obtenerTodos()).thenReturn(testTickets);

        // Act
        List<Ticket> result = ticketService.obtenerTodos();

        // Assert
        assertEquals(testTickets, result);
        verify(ticketRepository, times(1)).obtenerTodos();
    }

    @Test
    public void testObtenerPorId() {
        // Arrange
        when(ticketRepository.obtenerPorId(1L)).thenReturn(testTickets);

        // Act
        List<Ticket> result = ticketService.obtenerPorId(1L);

        // Assert
        assertEquals(testTickets, result);
        verify(ticketRepository, times(1)).obtenerPorId(1L);
    }

    @Test
    public void testActualizarEstado() {
        // Arrange
        when(ticketRepository.actualizarEstado(any(Ticket.class))).thenReturn(1);

        // Act
        int result = ticketService.actualizarEstado(testTicket);

        // Assert
        assertEquals(1, result);
        verify(ticketRepository, times(1)).actualizarEstado(testTicket);
    }

    @Test
    public void testMarcarComoResuelto() {
        // Arrange
        when(ticketRepository.marcarComoResuelto(any(Ticket.class))).thenReturn(1);

        // Act
        int result = ticketService.marcarComoResuelto(testTicket);

        // Assert
        assertEquals(1, result);
        verify(ticketRepository, times(1)).marcarComoResuelto(testTicket);
    }

    @Test
    public void testObtenerNoResueltos30Dias() {
        // Arrange
        when(ticketRepository.obtenerNoResueltos30Dias()).thenReturn(testTickets);

        // Act
        List<Ticket> result = ticketService.obtenerNoResueltos30Dias();

        // Assert
        assertEquals(testTickets, result);
        verify(ticketRepository, times(1)).obtenerNoResueltos30Dias();
    }
}