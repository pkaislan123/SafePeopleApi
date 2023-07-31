package br.com.safepeople.models;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transmissor")
public class Transmissor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_transmissor;
	
	private String numero_serie;
	private String marca;
	private String modelo;
	
	private String data_fabricacao;



	private int status;
	
	
	@OneToOne
	@JoinColumn( nullable = true, name = "fornecedor_id", referencedColumnName = "id_fornecedor")
	private Fornecedor fornecedor;
	
	

	
	@Column( nullable = true)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime data_hora_ultima_resposta;
	
	
	private int status_online;
	
	
	private String descricao;
	

	@OneToOne
	@JoinColumn( nullable = true, name = "receptor_id", referencedColumnName = "id_receptor")
	private Receptor receptor;
	
	


	@OneToOne
	@JoinColumn( nullable = true, name = "cliente_id", referencedColumnName = "id_cliente")
	private Cliente cliente;
	
	
}
