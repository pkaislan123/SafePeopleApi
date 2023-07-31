package br.com.safepeople.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.safepeople.models.Receptor;
import br.com.safepeople.models.Transmissor;



@Repository
public interface TransmissorRepository extends JpaRepository<Transmissor, Integer> {

	@Query(value = "select * from transmissor where fornecedor_id = :id", nativeQuery = true)
	List<Transmissor> listarPorFornecedor(int id);
	
	@Query(value = "select * from transmissor where numero_serie = :num", nativeQuery = true)
	Optional<Transmissor> listarPorNumeroSerie(int num);
	
	@Query(value = "select * from transmissor where receptor_id = :id", nativeQuery = true)
	List<Transmissor> listarPorReceptor(int id);
	
	
	@Query(value = "select * from transmissor where fornecedor_id = :id and status = 2", nativeQuery = true)
	List<Transmissor> listarAtivosPorFornecedor(int id);
	
}
