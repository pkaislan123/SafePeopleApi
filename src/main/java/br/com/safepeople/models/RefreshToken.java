package br.com.safepeople.models;

import java.time.Instant;
import javax.persistence.*;

import lombok.Data;

@Data
@Entity(name = "refreshtoken")
public class RefreshToken {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@OneToOne
	@JoinColumn( nullable = true, name = "user_id", referencedColumnName = "id_usuario")
	private User user;
	
	@OneToOne
	@JoinColumn( nullable = true, name = "fornecedor_id", referencedColumnName = "id_fornecedor")
	private Fornecedor fornecedor;
	
	@OneToOne
	@JoinColumn( nullable = true, name = "cliente_id", referencedColumnName = "id_cliente")
	private Cliente cliente;
	
	
	@Column(nullable = false, unique = true)
	private String token;
	
	
	@Column(nullable = false)
	private Instant expiryDate;


	
}
