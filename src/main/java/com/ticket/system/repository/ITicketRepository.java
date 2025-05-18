package com.ticket.system.repository;

import com.ticket.system.model.Ticket;

import java.util.List;

public interface ITicketRepository{
    public int crear(Ticket ticket);
    List<Ticket> obtenerTodos();
    List<Ticket> obtenerPorId(Long id);
    public int actualizarEstado(Ticket ticket);
    public int marcarComoResuelto(Ticket ticket);
    List<Ticket> obtenerNoResueltos30Dias();
}
