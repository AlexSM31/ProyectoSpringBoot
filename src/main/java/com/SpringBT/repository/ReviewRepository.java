package com.SpringBT.repository;

import com.SpringBT.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

	// Para el informe: Contar reviews por valoración (1-5 estrellas)
	@Query("SELECT r.valoracion, COUNT(r) FROM Review r GROUP BY r.valoracion ORDER BY r.valoracion")
	List<Object[]> countByValoracion();
}
