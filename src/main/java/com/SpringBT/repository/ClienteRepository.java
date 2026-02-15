package com.SpringBT.repository;

import com.SpringBT.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	// Para el informe: Contar clientes por género
	@Query("SELECT c.genero, COUNT(c) FROM Cliente c GROUP BY c.genero")
	List<Object[]> countByGenero();

	// Para el informe: Contar clientes por rango de edad
	@Query("SELECT CASE " + "WHEN c.edad < 15 THEN '0-14' " + "WHEN c.edad BETWEEN 15 AND 24 THEN '15-24' "
			+ "WHEN c.edad BETWEEN 25 AND 34 THEN '25-34' " + "WHEN c.edad BETWEEN 35 AND 44 THEN '35-44' "
			+ "WHEN c.edad BETWEEN 45 AND 54 THEN '45-54' " + "ELSE '55+' END AS rango, COUNT(c) "
			+ "FROM Cliente c GROUP BY CASE " + "WHEN c.edad < 15 THEN '0-14' "
			+ "WHEN c.edad BETWEEN 15 AND 24 THEN '15-24' " + "WHEN c.edad BETWEEN 25 AND 34 THEN '25-34' "
			+ "WHEN c.edad BETWEEN 35 AND 44 THEN '35-44' " + "WHEN c.edad BETWEEN 45 AND 54 THEN '45-54' "
			+ "ELSE '55+' END " + "ORDER BY rango")
	List<Object[]> countByRangoEdad();

	// Para gráfico personalizado: Clientes con intolerancias
	@Query("SELECT CASE WHEN c.intolerancia = true THEN 'Con Intolerancia' ELSE 'Sin Intolerancia' END, COUNT(c) FROM Cliente c GROUP BY c.intolerancia")
	List<Object[]> countByIntolerancia();
}
