package br.com.safepeople.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.safepeople.models.Fornecedor;


@Repository

public interface FornecedorRepository extends JpaRepository<Fornecedor, Integer>{

	@Query(value = "select * from fornecedor where cpf = ? or cnpj = ? limit 1", nativeQuery = true)
	  Optional<Fornecedor> findByCpfCnpj(String cpf, String cnpj);
	
	@Query(value = "select * from fornecedor where email = ? limit 1", nativeQuery = true)
	  Optional<Fornecedor> findByEmail(String email);
	
	@Query(value = "select * from fornecedor where cpf = ? or cnpj = ? ", nativeQuery = true)
	  List<Fornecedor> encontrarEnderecos(String cpf, String cnpj);
	
	 @Query(value = "select * from fornecedor"
		 		+ " where (cpf like %:cpf_cnpj% or cnpj like %:cpf_cnpj%)"
		 		+ " order by id_fornecedor"
		 		, nativeQuery = true)
		    Page<Fornecedor> listarFornecedoresPaginados(
		            @Param("cpf_cnpj") String cpf_cnpj, 
		            Pageable pageable);
	 
	
		Boolean existsByCpf(String cpf);

		Boolean existsByCnpj(String cnpj);

		Boolean existsByEmail(String email);
		

		
		
		
		
		
}
