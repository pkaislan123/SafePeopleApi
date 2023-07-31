package br.com.safepeople.utils;
import javax.validation.constraints.NotBlank;

import lombok.Data;


@Data
public class LoginRequest {
	@NotBlank
	private String login;

	@NotBlank
	private String senha;

	
}
