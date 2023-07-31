package br.com.safepeople.models;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "log")
public class Log {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_log;
	
	private int tipo;
	//0 login
	//1 logout
	//10 acao na central
	
	//11 mudar status de usuario

	//20 entrar na tela de tokens
	
	//21 vizualizar um token

	
	
	
	
	private LocalDateTime data_hora;
	
	private String mensagem;
	

	
	@OneToOne
	@JoinColumn( nullable = true, name = "fornecedor_id", referencedColumnName = "id_fornecedor")
	private Fornecedor fornecedor;
	
	@OneToOne
	@JoinColumn( nullable = true, name = "cliente_id", referencedColumnName = "id_cliente")
	private Cliente cliente;
	
	@OneToOne
	@JoinColumn( nullable = true, name = "user_id_acionador", referencedColumnName = "id_usuario")
	private User usuario_acionador;
	
	@OneToOne
	@JoinColumn( nullable = true, name = "user_id_acionado", referencedColumnName = "id_usuario")
	private User usuario_acionado;
	
	@OneToOne
	@JoinColumn( nullable = true, name = "cliente_id_acionado", referencedColumnName = "id_cliente")
	private Cliente cliente_acionado;
	
	
	
}
