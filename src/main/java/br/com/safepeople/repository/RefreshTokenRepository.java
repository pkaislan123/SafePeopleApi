package br.com.safepeople.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.safepeople.models.RefreshToken;
import br.com.safepeople.models.User;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

	  @Override
	    Optional<RefreshToken> findById(Long id);
	    Optional<RefreshToken> findByToken(String token);
	    
	    
		int deleteByUser(User user);
	    
}
