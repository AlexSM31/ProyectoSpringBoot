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

import com.SpringBT.dto.ReviewDTO;
import com.SpringBT.entity.Cliente;
import com.SpringBT.entity.Review;
import com.SpringBT.mapper.ReviewMapper;
import com.SpringBT.service.ClienteService;
import com.SpringBT.service.ReviewService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * RestController para gestionar operaciones CRUD de Review mediante API REST
 *   
 * Endpoints disponibles:
 * - GET -> Listar todas las reviews
 * - GET -> Obtener una review por ID
 * - POST -> Crear una nueva review
 * - PUT -> Actualizar una review existente
 * - DELETE -> Eliminar una review
 */

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@Validated
public class ReviewRestController {

    private final ReviewService reviewService;
    private final ClienteService clienteService;
    private final ReviewMapper reviewMapper;

    /**
     * GET /api/reviews
     * Lista todas las reviews.
     * 
     * @return Lista de ReviewDTOs con código 200 ok
     */
    @GetMapping
    public ResponseEntity<List<ReviewDTO>> getAllReviews() {
        List<Review> reviews = reviewService.findAll();
        
        List<ReviewDTO> reviewsDTO = reviews.stream()
                .map(reviewMapper::toDTO)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(reviewsDTO);
    }

    /**
     * GET /api/reviews/{id}
     * Obtiene una review específica por su ID.
     * 
     * @param id ID de la review
     * @return ReviewDTO con código 200 ok o 404 NOT found si no existe
     */
    @GetMapping("/{id}")
    public ResponseEntity<ReviewDTO> getReviewById(@PathVariable Long id) {
        return reviewService.findById(id)
                .map(review -> ResponseEntity.ok(reviewMapper.toDTO(review)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST /api/reviews
     * Crea una nueva review asociada a un cliente.
     * 
     * Validaciones:
     * - El cliente debe existir se busca por clienteID
     * - La valoración debe estar entre 1 y 5
     * 
     * @param reviewDTO Datos de la review a crear validados con @Valid
     * @return ReviewDTO creado con código 201 created o 404 si el cliente no existe
     */
    @PostMapping
    public ResponseEntity<ReviewDTO> createReview(@Valid @RequestBody ReviewDTO reviewDTO) {
        // Verificamos que el cliente existe
        Cliente cliente = clienteService.findById(reviewDTO.getClienteId())
                .orElse(null);
        
        if (cliente == null) {
            // Si el cliente no existe, devolvemos 404 NOT FOUND
            return ResponseEntity.notFound().build();
        }
        
        // Convertimos el DTO a entidad
        Review review = reviewMapper.toEntity(reviewDTO);
        
        // Establecemos la relación con el cliente
        review.setCliente(cliente);
        
        // Guardamos la review en la base de datos
        Review savedReview = reviewService.save(review);
        
        // Convertimos la entidad guardada a DTO
        ReviewDTO savedDTO = reviewMapper.toDTO(savedReview);
        
        // Devolvemos la respuesta con código 201 CREATED
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDTO);
    }

    /**
     * PUT /api/reviews/{id}
     * Actualiza una review existente.
     * 
     * @param id ID de la review a actualizar
     * @param reviewDTO Nuevos datos de la review validados con @Valid
     * @return ReviewDTO actualizado con código 200 ok o 404 not found si no existe
     */
    @PutMapping("/{id}")
    public ResponseEntity<ReviewDTO> updateReview(
            @PathVariable Long id,
            @Valid @RequestBody ReviewDTO reviewDTO) {
        
        return reviewService.findById(id)
                .map(reviewExistente -> {
                    // Actualizamos los campos de la review existente
                    reviewMapper.updateEntityFromDTO(reviewDTO, reviewExistente);
                    
                    // Guardamos los cambios
                    Review updatedReview = reviewService.save(reviewExistente);
                    
                    // Convertimos a DTO y devolvemos 200 OK
                    return ResponseEntity.ok(reviewMapper.toDTO(updatedReview));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * DELETE /api/reviews/{id}
     * Elimina una review por su ID.
     * 
     * @param id ID de la review a eliminar
     * @return 204 not content si se eliminó correctamente o 404 not found si no existe
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        if (reviewService.findById(id).isPresent()) {
            reviewService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        
        return ResponseEntity.notFound().build();
    }
}
