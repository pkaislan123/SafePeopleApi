package br.com.safepeople.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.safepeople.models.Cliente;



@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

	
	@Query(value = "select * from cliente where cpf = ? or cnpj = ? limit 1", nativeQuery = true)
	  Optional<Cliente> findByCpfCnpj(String cpf, String cnpj);
	
	
	@Query(value = "select * from cliente where email = ? limit 1", nativeQuery = true)
	  Optional<Cliente> findByEmail(String email);
	
	
	@Query(value = "select * from cliente where cpf = ? or cnpj = ? ", nativeQuery = true)
	  List<Cliente> encontrarEnderecos(String cpf, String cnpj);
	
	 @Query(value = "select * from cliente"
		 		+ " where (cpf like %:cpf_cnpj% or cnpj like %:cpf_cnpj%)"
		 		+ " order by id_cliente"
		 		, nativeQuery = true)
		    Page<Cliente> listarClientesPaginados(
		            @Param("cpf_cnpj") String cpf_cnpj, 
		            Pageable pageable);
	 
	 
	 @Query(value = "select * from cliente"
		 		+ " where (cpf like %:cpf_cnpj% or cnpj like %:cpf_cnpj%)"
		 		+ " and fornecedor_id = :idfornecedor"
		 		+ " order by id_cliente"
		 		, nativeQuery = true)
		    Page<Cliente> listarClientesPaginadosPorFornecedor(
		            @Param("cpf_cnpj") String cpf_cnpj, int idfornecedor,
		            Pageable pageable);
	 
	 
	 
	 
	 

		Boolean existsByCpf(String cpf);

		Boolean existsByCnpj(String cnpj);

		Boolean existsByEmail(String email);
		
		@Query(value = "select * from cliente where fornecedor_id = ?", nativeQuery = true)
		List<Cliente> findByFornecedorId(int fornecedor_id);
		

		@Transactional
		@Modifying
		@Query(value = "insert into cliente_cartao values(:id_cliente, :id_cartao)", nativeQuery = true)
		int inserirAssociacao(int id_cliente, int id_cartao);


}
