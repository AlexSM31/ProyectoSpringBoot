package com.SpringBT.service;

import com.SpringBT.entity.Review;
import com.SpringBT.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {

	private final ReviewRepository reviewRepository;

	public List<Review> findAll() {
		return reviewRepository.findAll();
	}

	public Optional<Review> findById(Long id) {
		return reviewRepository.findById(id);
	}

	@Transactional
	public Review save(Review review) {
		return reviewRepository.save(review);
	}

	@Transactional
	public void deleteById(Long id) {
		reviewRepository.deleteById(id);
	}

	public long count() {
		return reviewRepository.count();
	}

	// Método para el informe
	public List<Object[]> getReviewsPorValoracion() {
		return reviewRepository.countByValoracion();
	}
}
