package br.com.safepeople.models;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.boot.context.properties.bind.DefaultValue;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "fornecedor")
public class Fornecedor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_fornecedor;

	private int tipo_fornecedor;

	// dados de pessoa juridica
	private String cnpj;
	private String razao_social;
	private String nome_fantasia;

	// dados de pessoas fisicas
	private String cpf;
	private String rg;
	private String nascimento;
	private String nome;
	private String sobrenome;

	private String porte;
	private String atividade;
	private String ie;
	private String descricao;

	@NotBlank
	@Size(max = 255)
	@Column(name = "email")
	private String email;

	@NotBlank
	@Size(max = 255)
	@Column(name = "senhaweb")
	private String senhaweb;
	
	
	@Size(max = 1000)
	@Column(name = "token")
	private String token;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime data_cadastro;

	private int status;
	
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

	@ManyToMany
	@Fetch(FetchMode.JOIN)
	@JoinTable(name = "fornecedor_contato", joinColumns = @JoinColumn(name = "id_fornecedor"), inverseJoinColumns = @JoinColumn(name = "id_contato"))
	private Set<Contato> contatos = new HashSet<>();

	@ManyToMany
	@Fetch(FetchMode.JOIN)
	@JoinTable(name = "fornecedor_conta", joinColumns = @JoinColumn(name = "id_fornecedor"), inverseJoinColumns = @JoinColumn(name = "id_conta"))
	private Set<Contato> contas = new HashSet<>();


	
	
	private String latitude;
	private String longitude;

}
