package br.com.safepeople.utils;


import java.time.LocalDateTime;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpClienteRequest {

	@NotNull
	private int tipo_cliente;
	
	@Size(max = 11, min = 11)
	private String cpf;
	
	@Size(max = 14, min = 14)
	private String cnpj;
	
	@Size(max = 100)
	private String razao_social;
	
	@Size(max = 100)
	private String nome_fantasia;
	
	@Size(max = 100)
	private String nome;
	
	@Size(max = 100)
	private String sobrenome;
	
	@Size(max = 11)
	private String nascimento;
	
	private String rg;
	
	
	private String porte;
	private String atividade;
	private String ie;
	private String descricao;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime data_cadastro;
 
	@NotBlank
	@Size(max = 50)
	@Email
	private String email;
    
    
    @NotBlank
    @Size(min = 8, max = 40)
    private String senhaweb;
    
    @NotNull
    private int id_fornecedor;

	private String info_adicional;
	
	private String rua;
	private String bloco;
	private String numero;
	private String apartamento;
	private String complemento;

  
	
}