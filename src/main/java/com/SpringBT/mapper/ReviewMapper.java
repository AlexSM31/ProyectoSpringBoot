package com.SpringBT.mapper;

import org.springframework.stereotype.Component;

import com.SpringBT.dto.ReviewDTO;
import com.SpringBT.entity.Review;

/**
 * Mapper para convertir entre Review (Entity) y ReviewDTO.
 */
@Component
public class ReviewMapper {

    /**
     * Convierte una entidad Review a ReviewDTO.
     * @param review La entidad a convertir
     * @return El DTO correspondiente
     */
    public ReviewDTO toDTO(Review review) {
        if (review == null) {
            return null;
        }

        ReviewDTO dto = new ReviewDTO();
        dto.setId(review.getId());
        dto.setDescripcion(review.getDescripcion());
        dto.setValoracion(review.getValoracion());

        // Incluimos información del cliente asociado
        if (review.getCliente() != null) {
            dto.setClienteId(review.getCliente().getId());
            dto.setClienteNombre(review.getCliente().getNombre());
        }

        return dto;
    }

    /**
     * Convierte un ReviewDTO a entidad Review.
     * @param dto El DTO a convertir
     * @return La entidad correspondiente
     */
    public Review toEntity(ReviewDTO dto) {
        if (dto == null) {
            return null;
        }

        Review review = new Review();
        review.setId(dto.getId());
        review.setDescripcion(dto.getDescripcion());
        review.setValoracion(dto.getValoracion());

        return review;
    }

    /**
     * Actualiza una entidad Review existente con datos de un DTO.
     * @param dto El DTO con los nuevos datos
     * @param review La entidad existente a actualizar
     */
    public void updateEntityFromDTO(ReviewDTO dto, Review review) {
        if (dto == null || review == null) {
            return;
        }

        review.setDescripcion(dto.getDescripcion());
        review.setValoracion(dto.getValoracion());
    }
}
