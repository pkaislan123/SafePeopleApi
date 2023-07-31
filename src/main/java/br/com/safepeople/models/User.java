package br.com.safepeople.models;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuarios", uniqueConstraints = { @UniqueConstraint(columnNames = { "login" }) })
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_usuario;

	@NotBlank
	@Size(max = 20)
	private String login;

	@NotBlank
	@Size(max = 255)
	@Column(name = "senhaweb")
	private String senha;

	private String url_img_perfil;
	
	private String sobre;
	
	private String nome;

	private String sobrenome;

	private String cargo;
	private String email;

	
	public User(String username, String password) {
		this.login = username;
		this.senha = password;
	}
	
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime data_cadastro;

	private String cpf;
	private String rg;
	
	@Column(columnDefinition = "integer default 0")
	private int tipo_endereco;

	@NotEmpty
	private String cep;

	@NotEmpty
	private String logradouro;

	@NotEmpty
	private String bairro;

	@NotEmpty
	private String numero;

	@NotEmpty
	private String cidade;

	@NotEmpty
	private String estado;

	@NotEmpty
	private String complemento;

	private String info_adicional;
	
	
	//1 ativo
	//0 bloqueado
	@Column(columnDefinition = "integer default 1")
	private int status;
	
	private String roles;

}