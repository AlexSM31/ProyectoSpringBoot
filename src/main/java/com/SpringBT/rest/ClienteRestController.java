package com.SpringBT.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SpringBT.dto.ClienteDTO;
import com.SpringBT.entity.Cliente;
import com.SpringBT.mapper.ClienteMapper;
import com.SpringBT.service.ClienteService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * RestController para gestionar operaciones CRUD de Cliente mediante API REST
 * 
 * - @RestController devuelve datos JSON
 * 
 * Endpoints:
 * - GET -> Listar todos los clientes
 * - GET -> Obtener un cliente por ID
 * - POST -> Crear un nuevo cliente
 * - PUT  -> Actualizar un cliente existente
 * - DELETE -> Eliminar un cliente
 */

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
@Validated
public class ClienteRestController {

    // Inyección de dependencias mediante constructor
    private final ClienteService clienteService;
    private final ClienteMapper clienteMapper;

    /**
     * GET /api/clientes
     * Lista todos los clientes.
     * 
     * @return Lista de ClienteDTOs con código 200 OK
     */
    @GetMapping
    public ResponseEntity<List<ClienteDTO>> getAllClientes() {
        // Obtenemos todas las entidades del servicio
        List<Cliente> clientes = clienteService.findAll();
        
        // Convertimos cada entidad a DTO usando el mapper
        List<ClienteDTO> clientesDTO = clientes.stream()
                .map(clienteMapper::toDTO)
                .collect(Collectors.toList());
        
        // Devolvemos la respuesta con código 200 OK
        return ResponseEntity.ok(clientesDTO);
    }

    /**
     * GET /api/clientes/{id}
     * Obtiene un cliente específico por su ID.
     * 
     * @param id ID del cliente
     * @return ClienteDTO con código 200 ok o 404 not found si no existe
     */
    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> getClienteById(@PathVariable Long id) {
        // 1. Buscamos el cliente por ID (devuelve Optional<Cliente>)
        return clienteService.findById(id)
                // 2. Si existe, lo convertimos a DTO y devolvemos 200 OK
                .map(cliente -> ResponseEntity.ok(clienteMapper.toDTO(cliente)))
                // 3. Si no existe, devolvemos 404 NOT FOUND
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST /api/clientes
     * Crea un nuevo cliente.
     * 
     * @param clienteDTO Datos del cliente a crear (validados con @Valid)
     * @return ClienteDTO creado con código 201 CREATED
     */
    @PostMapping
    public ResponseEntity<ClienteDTO> createCliente(@Valid @RequestBody ClienteDTO clienteDTO) {
        // Convertimos el DTO a entidad
        Cliente cliente = clienteMapper.toEntity(clienteDTO);
        
        // Guardamos la entidad en la base de datos
        Cliente savedCliente = clienteService.save(cliente);
        
        // Convertimos la entidad guardada a DTO
        ClienteDTO savedDTO = clienteMapper.toDTO(savedCliente);
        
        // Devolvemos la respuesta con código 201 CREATED
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDTO);
    }

    /**
     * PUT /api/clientes/{id}
     * Actualiza un cliente existente.
     * 
     * @param id ID del cliente a actualizar
     * @param clienteDTO Nuevos datos del cliente 
     * @return ClienteDTO actualizado con código 200 ok o 404 not found si no existe
     */
    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> updateCliente(
            @PathVariable Long id,
            @Valid @RequestBody ClienteDTO clienteDTO) {
        
        // Buscamos el cliente existente
        return clienteService.findById(id)
                .map(clienteExistente -> {
                    // Actualizamos los campos del cliente existente con los datos del DTO
                    clienteMapper.updateEntityFromDTO(clienteDTO, clienteExistente);
                    
                    // Guardamos los cambios
                    Cliente updatedCliente = clienteService.save(clienteExistente);
                    
                    // Convertimos a DTO y devolvemos 200 ok
                    return ResponseEntity.ok(clienteMapper.toDTO(updatedCliente));
                })
                // Si no existe, devolvemos 404 not found
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * DELETE /api/clientes/{id}
     * Elimina un cliente por su ID.
     * 
     * @param id ID del cliente a eliminar
     * @return 204 not content si se eliminó correctamente, o 404 not found si no existe
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        // Verificamos si el cliente existe
        if (clienteService.findById(id).isPresent()) {
            // Si existe, lo eliminamos
            clienteService.deleteById(id);
            
            // Devolvemos 204 not content 
            return ResponseEntity.noContent().build();
        }
        
        // 4. Si no existe, devolvemos 404 not found
        return ResponseEntity.notFound().build();
    }
}
