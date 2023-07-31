package br.com.safepeople.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.safepeople.configs.JwtUtils;
import br.com.safepeople.services.CustomClienteDetails;
import br.com.safepeople.services.CustomFornecedorDetails;
import br.com.safepeople.services.FornecedoresService;
import br.com.safepeople.services.RefreshTokenService;
import br.com.safepeople.services.UserDetailsImpl;
import br.com.safepeople.services.UsersService;
import br.com.safepeople.models.Cliente;
import br.com.safepeople.models.Fornecedor;
import br.com.safepeople.models.RefreshToken;
import br.com.safepeople.models.User;
import br.com.safepeople.repository.ClienteRepository;
import br.com.safepeople.repository.FornecedorRepository;
import br.com.safepeople.repository.UserRepository;
import br.com.safepeople.utils.JwtResponse;
import br.com.safepeople.utils.LoginRequest;
import br.com.safepeople.utils.MessageResponse;
import br.com.safepeople.utils.SignUpRequest;
import br.com.safepeople.utils.SignUpResponse;
import br.com.safepeople.utils.TokenRefreshRequest;
import br.com.safepeople.utils.TokenRefreshResponse;
import br.com.safepeople.exceptions.TokenRefreshException;

@CrossOrigin
@RequestMapping({ "v1" })
@RestController
@Api("Acesso")
public class AcessoController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	ClienteRepository clienteRepository;

	@Autowired
	FornecedorRepository fornecedorRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;
	

	@Autowired
	UsersService service;

	@Autowired
	RefreshTokenService refreshTokenService;

	@CrossOrigin
	@PostMapping({ "protected/signin" })
	public ResponseEntity<?> authenticateClient(@Valid @RequestBody LoginRequest login) {
		try {

			Authentication authentication = this.authenticationManager.authenticate(
					(Authentication) new UsernamePasswordAuthenticationToken(login.getLogin(), login.getSenha()));

			SecurityContextHolder.getContext().setAuthentication(authentication);

			String jwt = this.jwtUtils.generateJwtToken(authentication);

			CustomClienteDetails userDetails = (CustomClienteDetails) authentication.getPrincipal();

			RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId(), 0);

			
			
			return ResponseEntity.ok(new JwtResponse(1, jwt, refreshToken.getToken(), userDetails.getId()));

		} catch (Exception e) {

			try {
				Authentication authentication = this.authenticationManager.authenticate(
						(Authentication) new UsernamePasswordAuthenticationToken(login.getLogin(), login.getSenha()));
				SecurityContextHolder.getContext().setAuthentication(authentication);

				String jwt = this.jwtUtils.generateJwtToken(authentication);

				UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

				RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId(), 1);

				if(userDetails.getStatus() == 0) {
			        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("bloqueado");

				}else {
					return ResponseEntity.ok(new JwtResponse(userDetails.getRoles(), jwt, refreshToken.getToken(), userDetails.getId()));

				}
				

			} catch (Exception f) {

				Authentication authentication = this.authenticationManager.authenticate(
						(Authentication) new UsernamePasswordAuthenticationToken(login.getLogin(), login.getSenha()));
				SecurityContextHolder.getContext().setAuthentication(authentication);
				String jwt = this.jwtUtils.generateJwtToken(authentication);

				CustomFornecedorDetails userDetails = (CustomFornecedorDetails) authentication.getPrincipal();

				RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId(), 2);

				return ResponseEntity.ok(new JwtResponse(2, jwt, refreshToken.getToken(), userDetails.getId()));

			}
		}
	}

	@CrossOrigin
	@PostMapping("protected/refreshtoken")
	public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {

		String requestRefreshToken = request.getRefreshToken();
		ResponseEntity<?> response = null;

		if (request.getRegra().equals("ROLE_ADMIN")) {
			response = refreshTokenService.findByToken(requestRefreshToken).map(refreshTokenService::verifyExpiration)
					.map(RefreshToken::getUser).map(user -> {

						String token = jwtUtils.generateTokenFromUsername(user.getLogin());

						return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
					}).orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
							"Refresh token user is not in database!"));

		} else if (request.getRegra().equals("ROLE_CLIENTE")) {
			response = refreshTokenService.findByToken(requestRefreshToken).map(refreshTokenService::verifyExpiration)
					.map(RefreshToken::getCliente).map(cliente -> {

						String token = jwtUtils.generateTokenFromUsername(
								cliente.getTipo_cliente() == 0 ? cliente.getCpf() : cliente.getCnpj());

						return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
					}).orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
							"Refresh token cliente is not in database!"));

		} else if (request.getRegra().equals("ROLE_FORNECEDOR")) {
			response = refreshTokenService.findByToken(requestRefreshToken).map(refreshTokenService::verifyExpiration)
					.map(RefreshToken::getFornecedor).map(fornecedor -> {

						String token = jwtUtils.generateTokenFromUsername(
								fornecedor.getTipo_fornecedor() == 0 ? fornecedor.getCpf() : fornecedor.getCnpj());

						return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
					}).orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
							"Refresh token fornecedor is not in database!"));

		}

		return response;
	}

	@ApiOperation(value = "Verificar Por PF Já Cadastrada")
	@CrossOrigin
	@GetMapping("protected/signuppfverify/{identificacao}")
	public SignUpResponse verificarPreCadastroPF(@PathVariable String identificacao) {

		SignUpResponse response = new SignUpResponse();

		if (clienteRepository.existsByCpf(identificacao)) {
			response.setResposta("Error: CPF Já Cadastrado");
		}

		else if (fornecedorRepository.existsByCpf(identificacao)) {
			response.setResposta("Error: CPF Já Cadastrado");
		} else {
			response.setResposta("OK");
		}

		return response;

	}

	@CrossOrigin
	@GetMapping("protected/signuppjverify/{identificacao}")
	public SignUpResponse verificarPreCadastroPJ(@PathVariable String identificacao) {

		SignUpResponse response = new SignUpResponse();

		if (clienteRepository.existsByCnpj(identificacao)) {
			response.setResposta("Error: CNPJ Já Cadastrado");
		}

		else if (fornecedorRepository.existsByCnpj(identificacao)) {
			response.setResposta("Error: CNPJ Já Cadastrado");
		} else {
			response.setResposta("OK");
		}

		return response;

	}

	@CrossOrigin
	@GetMapping("protected/signupemailverify/{email}")
	public SignUpResponse verificarPreCadastroEmail(@PathVariable String email) {

		SignUpResponse response = new SignUpResponse();

		if (userRepository.existsByEmail(email)) {
			response.setResposta("Error: Email Já Cadastrado");
		}

		else if (fornecedorRepository.existsByEmail(email)) {
			response.setResposta("Error: Email Já Cadastrado");
		} else if (clienteRepository.existsByEmail(email)) {
			response.setResposta("Error: Email Já Cadastrado");
		}

		else {
			response.setResposta("OK");
		}

		return response;

	}
	
	
	@GetMapping("protected/signuploginverify/{login}")
	public SignUpResponse verificarPreCadastroLogin(@PathVariable String login) {

		SignUpResponse response = new SignUpResponse();

		if (userRepository.existsByLogin(login)) {
			response.setResposta("Error: Login Já Cadastrado");
		}
		else {
			response.setResposta("OK");
		}

		return response;

	}
	
	
	@GetMapping("protected/users/listar")
	public Page<User> search(
			@RequestParam(value = "cpf", required = false, defaultValue = "") String cpf,
			@RequestParam(value = "page", required = true) int page,
			@RequestParam(value = "size", required = false, defaultValue = "50") int size)

	{
		return service.listAllClientes(page, size, cpf);

	}
	
	@CrossOrigin
	@GetMapping("protected/users/listarTodos")
	public List<User> listarUsuarios() {

		return userRepository.findAll();

	}
	

}