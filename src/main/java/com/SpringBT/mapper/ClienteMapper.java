package com.SpringBT.mapper;

import org.springframework.stereotype.Component;

import com.SpringBT.dto.ClienteDTO;
import com.SpringBT.entity.Cliente;

/**
 * Mapper para convertir entre Cliente (Entity) y ClienteDTO.
 */
@Component
public class ClienteMapper {

    /**
     * Lo usamos cuando devolvemos datos al cliente (GET).
     * @param cliente La entidad a convertir
     * @return El DTO correspondiente
     */
    public ClienteDTO toDTO(Cliente cliente) {
        if (cliente == null) {
            return null;
        }

        ClienteDTO dto = new ClienteDTO();
        dto.setId(cliente.getId());
        dto.setNombre(cliente.getNombre());
        dto.setEdad(cliente.getEdad());
        dto.setGenero(cliente.getGenero());
        dto.setIntolerancia(cliente.getIntolerancia());
        dto.setDetalleIntolerancia(cliente.getDetalleIntolerancia());

        // Si el cliente tiene una review, guardamos su ID
        if (cliente.getReview() != null) {
            dto.setReviewId(cliente.getReview().getId());
        }

        return dto;
    }

    /**
     * Convierte un ClienteDTO a entidad Cliente.
     * Lo usamos cuando recibimos datos del cliente (POST/PUT).
     * 
     * @param dto El DTO a convertir
     * @return La entidad correspondiente
     */
    public Cliente toEntity(ClienteDTO dto) {
        if (dto == null) {
            return null;
        }

        Cliente cliente = new Cliente();
        cliente.setId(dto.getId());
        cliente.setNombre(dto.getNombre());
        cliente.setEdad(dto.getEdad());
        cliente.setGenero(dto.getGenero());
        cliente.setIntolerancia(dto.getIntolerancia());
        cliente.setDetalleIntolerancia(dto.getDetalleIntolerancia());

        return cliente;
    }

    /**
     * Actualiza una entidad Cliente existente con datos de un DTO.     * 
     * @param dto El DTO con los nuevos datos
     * @param cliente La entidad existente a actualizar
     */
    public void updateEntityFromDTO(ClienteDTO dto, Cliente cliente) {
        if (dto == null || cliente == null) {
            return;
        }

        cliente.setNombre(dto.getNombre());
        cliente.setEdad(dto.getEdad());
        cliente.setGenero(dto.getGenero());
        cliente.setIntolerancia(dto.getIntolerancia());
        cliente.setDetalleIntolerancia(dto.getDetalleIntolerancia());
    }
}
