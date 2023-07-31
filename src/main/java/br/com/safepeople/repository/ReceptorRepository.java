package br.com.safepeople.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.safepeople.models.Receptor;
import br.com.safepeople.models.Transmissor;

@Repository
public interface ReceptorRepository extends JpaRepository<Receptor, Integer> {

	@Query(value = "select * from receptor where fornecedor_id = :id", nativeQuery = true)
	List<Receptor> listarPorFornecedor(int id);
	
	
	@Query(value = "select * from receptor where numero_serie = :num", nativeQuery = true)
	Optional<Receptor> listarPorNumeroSerie(int num);
	
	
	
}
