package com.SpringBT.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.SpringBT.entity.Cliente;
import com.SpringBT.entity.Review;
import com.SpringBT.repository.ReviewRepository;

/**
 * Test Unitario de ReviewService.
 */
@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewService reviewService;

    private Review review;

    @BeforeEach
    void setUp() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNombre("Juan Pérez");

        review = new Review();
        review.setId(1L);
        review.setDescripcion("Excelente servicio");
        review.setValoracion(5);
        review.setCliente(cliente);
    }

    /**
     * Test 1: Verificar que findById() devuelve la review.
     */
    @Test
    void testFindById() {
        // Simulamos que el repositorio encuentra la review
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));

        // Llamamos al método del servicio
        Optional<Review> resultado = reviewService.findById(1L);

        // Verificamos que funciona
        assertTrue(resultado.isPresent());
        assertEquals(5, resultado.get().getValoracion());
        assertEquals("Excelente servicio", resultado.get().getDescripcion());
    }

    /**
     * Test 2: Verificar que deleteById() elimina la review.
     */
    @Test
    void testDeleteById() {
        // Simulamos que el repositorio elimina sin error
        doNothing().when(reviewRepository).deleteById(1L);

        // Llamamos al método del servicio
        reviewService.deleteById(1L);

        // Verificamos que se llamó al repositorio
        verify(reviewRepository, times(1)).deleteById(1L);
    }
}
