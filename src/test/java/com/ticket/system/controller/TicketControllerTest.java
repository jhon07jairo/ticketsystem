package com.ticket.system.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticket.system.model.ServiceResponse;
import com.ticket.system.model.Ticket;
import com.ticket.system.service.ITicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class TicketControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ITicketService ticketService;

    @InjectMocks
    private TicketController ticketController;

    private ObjectMapper objectMapper = new ObjectMapper();

    private Ticket testTicket;
    private List<Ticket> testTickets;

    @BeforeEach
    public void setup() {
        // Initialize MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(ticketController).build();

        // Configure ObjectMapper for LocalDateTime serialization
        objectMapper.findAndRegisterModules();

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
    public void testCrearTicket() throws Exception {
        // Arrange
        when(ticketService.crear(any(Ticket.class))).thenReturn(1);

        // Act & Assert
        mockMvc.perform(post("/api/tickets/crear")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testTicket)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message", is("Ticket creado exitosamente")));
    }

    @Test
    public void testActualizarEstado() throws Exception {
        // Arrange
        when(ticketService.actualizarEstado(any(Ticket.class))).thenReturn(1);

        // Act & Assert
        mockMvc.perform(post("/api/tickets/actualizar-estado")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testTicket)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message", is("Estado de ticket actualizado exitosamente")));
    }

    @Test
    public void testMarcarComoResuelto() throws Exception {
        // Arrange
        when(ticketService.marcarComoResuelto(any(Ticket.class))).thenReturn(1);

        // Act & Assert
        mockMvc.perform(post("/api/tickets/marcar-resuelto")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testTicket)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message", is("Ticket marcado como resuelto exitosamente")));
    }

    @Test
    public void testObtenerTodos() throws Exception {
        // Arrange
        when(ticketService.obtenerTodos()).thenReturn(testTickets);

        // Act & Assert
        mockMvc.perform(get("/api/tickets/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].titulo", is("Test Ticket")))
                .andExpect(jsonPath("$[0].descripcion", is("Test Description")));
    }

    @Test
    public void testObtenerNoResueltos30Dias() throws Exception {
        // Arrange
        when(ticketService.obtenerNoResueltos30Dias()).thenReturn(testTickets);

        // Act & Assert
        mockMvc.perform(get("/api/tickets/no-resueltos-30-dias"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].titulo", is("Test Ticket")))
                .andExpect(jsonPath("$[0].descripcion", is("Test Description")));
    }
}
