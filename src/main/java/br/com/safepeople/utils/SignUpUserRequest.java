package br.com.safepeople.utils;

import java.time.LocalDateTime;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpUserRequest {

	@Size(max = 11, min = 11)
	private String cpf;

	@Size(max = 100)
	private String nome;

	@Size(max = 100)
	private String sobrenome;

	private String rg;

	private String cargo;

	@NotBlank
	@Size(min = 5, max = 40)
	private String login;

	@NotBlank
	@Size(max = 50)
	@Email
	private String email;

	@NotBlank
	@Size(min = 8, max = 40)
	private String senhaweb;

	private String cep;

	@NotNull
	private int tipo_endereco;

	private String logradouro;

	private String bairro;

	private String numero;

	private String cidade;
	
	private String estado;

	private String sobre;

	private String complemento;

	@NotBlank
	private String roles;

	private String url_img_perfil;

}
