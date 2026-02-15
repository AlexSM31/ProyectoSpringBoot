package com.SpringBT.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "clientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String nombre;

	@Column(nullable = false)
	private Integer edad;

	@Column(nullable = false)
	private String genero;

	@Column(nullable = false)
	private Boolean intolerancia = false;

	@Column(length = 500)
	private String detalleIntolerancia;
	
	

	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getNombre() {
		return nombre;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public Integer getEdad() {
		return edad;
	}



	public void setEdad(Integer edad) {
		this.edad = edad;
	}



	public String getGenero() {
		return genero;
	}



	public void setGenero(String genero) {
		this.genero = genero;
	}



	public Boolean getIntolerancia() {
		return intolerancia;
	}



	public void setIntolerancia(Boolean intolerancia) {
		this.intolerancia = intolerancia;
	}



	public String getDetalleIntolerancia() {
		return detalleIntolerancia;
	}



	public void setDetalleIntolerancia(String detalleIntolerancia) {
		this.detalleIntolerancia = detalleIntolerancia;
	}



	public Review getReview() {
		return review;
	}



	public void setReview(Review review) {
		this.review = review;
	}



	// OPCIONAL: Relación con Review (un cliente puede tener una review o ninguna)
	@OneToOne(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
	private Review review;
}
