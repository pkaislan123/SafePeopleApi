package br.com.safepeople.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.safepeople.models.Fornecedor;
import br.com.safepeople.models.User;


@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	  Optional<User> findByLogin(String paramString);

	  @Query(value = "select * from usuarios where id_usuario = ? ", nativeQuery = true)
	  Optional<User> findByIdUsuario(int id);
	 
		Boolean existsByEmail(String email);
		
		 @Query(value = "select * from usuarios"
			 		+ " where (cpf like %:cpf%)"
			 		+ " order by id_usuario"
			 		, nativeQuery = true)
			    Page<User> listarUsuariosPaginados(
			            @Param("cpf") String cpf, 
			            Pageable pageable);
		 
		 Boolean existsByLogin(String login);
		
		
		
}
