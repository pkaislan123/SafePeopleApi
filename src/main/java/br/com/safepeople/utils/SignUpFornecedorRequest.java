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
public class SignUpFornecedorRequest {

	@NotNull
	private int tipo_fornecedor;
	
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
    
    @NotBlank
    private String cep;
    
    @NotNull
    private int tipo_endereco;

    @NotBlank
	private String logradouro;

    @NotBlank
	private String bairro;

    @NotBlank
	private String numero;

    @NotBlank
	private String cidade;

    @NotBlank
	private String estado;

    @NotBlank
	private String complemento;

	private String info_adicional;
  
	
}