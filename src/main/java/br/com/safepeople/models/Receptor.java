package br.com.safepeople.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Column;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "receptor")
public class Receptor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_receptor;

	private String numero_serie;
	private String marca;
	private String modelo;

	private String data_fabricacao;

	private String descricao;

	private int status;
	
	private int status_online;


	@OneToOne
	@JoinColumn(nullable = true, name = "fornecedor_id", referencedColumnName = "id_fornecedor")
	private Fornecedor fornecedor;

	@NotBlank
	@Size(max = 255)
	@Column(name = "senhaweb")
	private String senhaweb;

	@Column(nullable = true)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime data_hora_ultima_resposta;

	

	@ManyToMany
	@Fetch(FetchMode.JOIN)
	@JoinTable(name = "receptor_transmissores", joinColumns = @JoinColumn(name = "id_receptor"), inverseJoinColumns = @JoinColumn(name = "id_transmissor"))
	private Set<Transmissor> transmissores = new HashSet<>();

	

	private String latitude;
	private String longitude;

	@Transient
	private int status_central;

	
	
	@Transient
	private LocalDateTime data_hora_ultima_resposta_central;

}
