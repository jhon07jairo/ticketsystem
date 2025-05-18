package com.ticket.system.service;

import com.ticket.system.model.Ticket;

import java.util.List;
import java.util.Optional;

public interface ITicketService {
    public int crear(Ticket ticket);
    List<Ticket> obtenerTodos();
    List<Ticket> obtenerPorId(Long id);
    public int actualizarEstado(Long id, Long estadoId);
    public int marcarComoResuelto(Ticket ticket);
    List<Ticket> obtenerNoResueltos30Dias();
}
