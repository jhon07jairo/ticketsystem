package com.ticket.system.controller;

import com.ticket.system.model.ServiceResponse;
import com.ticket.system.model.Ticket;
import com.ticket.system.service.ITicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@CrossOrigin("*")
public class TicketController {

    @Autowired
    private ITicketService iTicketService;

    @PostMapping("/crear")
    public ResponseEntity<ServiceResponse> crear(@RequestBody Ticket ticket) {
        ServiceResponse serviceResponse = new ServiceResponse();
        int result = iTicketService.crear(ticket);
        if (result == 1){
            serviceResponse.setMessage("Ticket creado exitosamente");
        }
        return new ResponseEntity<>(serviceResponse, HttpStatus.CREATED);
    }

    @PostMapping("/actualizar-estado")
    public ResponseEntity<ServiceResponse> actualizarEstado(@RequestBody Ticket ticket) {
        ServiceResponse serviceResponse = new ServiceResponse();
        int result = iTicketService.actualizarEstado(ticket);
        if (result == 1){
            serviceResponse.setMessage("Estado de ticket actualizado exitosamente");
        }
        return new ResponseEntity<>(serviceResponse, HttpStatus.CREATED);
    }

    @PostMapping("/marcar-resuelto")
    public ResponseEntity<ServiceResponse> marcarComoResuelto(@RequestBody Ticket ticket) {
        ServiceResponse serviceResponse = new ServiceResponse();
        int result = iTicketService.marcarComoResuelto(ticket);
        if (result == 1){
            serviceResponse.setMessage("Ticket marcado como resuelto exitosamente");
        }
        return new ResponseEntity<>(serviceResponse, HttpStatus.CREATED);
    }
    
    
    @GetMapping("/ticketbyid/{id}")
    public ResponseEntity<List<Ticket>> obtenerPorId(@PathVariable Long id) {
        var result = iTicketService.obtenerPorId (id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Ticket>> obtenerTodos() {
        var result = iTicketService.obtenerTodos();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping("/no-resueltos-30-dias")
    public ResponseEntity<List<Ticket>> obtenerNoResueltos30Dias() {
        var result = iTicketService.obtenerNoResueltos30Dias();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }




}
