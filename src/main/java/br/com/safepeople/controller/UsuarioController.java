package br.com.safepeople.controller;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.safepeople.models.Cliente;

import br.com.safepeople.models.Fornecedor;
import br.com.safepeople.models.Log;
import br.com.safepeople.models.User;
import br.com.safepeople.repository.LogRepository;
import br.com.safepeople.repository.UserRepository;
import br.com.safepeople.utils.MessageResponse;
import br.com.safepeople.utils.SignUpFornecedorRequest;
import br.com.safepeople.utils.SignUpUserRequest;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("v1/")
public class UsuarioController {

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	LogRepository logRepository;

	@CrossOrigin
	@GetMapping(path = "protected/user/retornardadosusuario/{id}")
	public Optional<User> retornarDadosUsuario(@PathVariable int id) {

		return userRepository.findById(id);
	}

	@CrossOrigin
	@ApiOperation(value = "Alterações no Usuário")
	@PutMapping(path = "protected/user/alterar/{id_usuario}")
	public ResponseEntity alterarUsuario(@PathVariable("id_usuario") int id_usuario, @RequestBody User user) {
		return userRepository.findById(id_usuario).map(record -> {

			record.setSobre(user.getSobre());
			record.setUrl_img_perfil(user.getUrl_img_perfil());
			// )

			User updated = userRepository.save(record);

			return ResponseEntity.ok().body(updated);
		}).orElse(ResponseEntity.notFound().build());
	}

	@PostMapping("protected/users/cadastrar")
	public ResponseEntity cadastrarUsuario(@Valid @RequestBody SignUpUserRequest signUpRequest) {

		// Create new user's account
		User user = new User();

		// pf
		user.setCpf(signUpRequest.getCpf());
		user.setRg(signUpRequest.getRg());
		user.setNome(signUpRequest.getNome());
		user.setSobrenome(signUpRequest.getSobrenome());

		// endereco
		user.setTipo_endereco(signUpRequest.getTipo_endereco());
		user.setLogradouro(signUpRequest.getLogradouro());
		user.setNumero(signUpRequest.getNumero());
		user.setBairro(signUpRequest.getBairro());
		user.setComplemento(signUpRequest.getComplemento());
		user.setCep(signUpRequest.getCep());
		user.setCidade(signUpRequest.getCidade());
		user.setEstado(signUpRequest.getEstado());

		user.setCargo(signUpRequest.getCargo());
		user.setRoles(signUpRequest.getRoles());
		user.setUrl_img_perfil(signUpRequest.getUrl_img_perfil());
		user.setLogin(signUpRequest.getLogin());
		user.setEmail(signUpRequest.getEmail());
		user.setStatus(1);
		user.setSenha(encoder.encode(signUpRequest.getSenhaweb()));
		user.setData_cadastro(LocalDateTime.now());
		user.setSobre(signUpRequest.getSobre());

		user = userRepository.saveAndFlush(user);

		if (user != null) {

			return ResponseEntity.ok(new MessageResponse("Fornecedor registered successfully!"));

		} else {
			return ResponseEntity.notFound().build();
		}

	}

	@CrossOrigin
	@PutMapping(path = "protected/users/mudarStatus/{id_usuario_acionador}/{id_usuario_acionado}/{mensagem}")
	public ResponseEntity mudarStatusUsuario(@PathVariable("id_usuario_acionador") int id_usuario_acionador,@PathVariable("id_usuario_acionado") int id_usuario_acionado, @PathVariable("mensagem") String mensagem) {
		
		User usuario_acionador = userRepository.findById(id_usuario_acionador).get();
		User usuario_acionado = userRepository.findById(id_usuario_acionado).get();

		
		int status_atual = usuario_acionado.getStatus();
		
		
		if(status_atual == 1) {
			usuario_acionado.setStatus(0);
		}else {
			usuario_acionado.setStatus(1);
		}
		
		User updated = userRepository.save(usuario_acionado);
		
		
		
		//criar log
		
		Log log = new Log();
		
		log.setData_hora(LocalDateTime.now());
		log.setUsuario_acionador(usuario_acionador);
		log.setUsuario_acionado(usuario_acionado);
		log.setTipo(3);
		log.setMensagem("Status do usuário foi modificado para " + (status_atual == 1 ? "DESATIVADO" : "ATIVADO") + "\n" + mensagem);
		
		
		logRepository.save(log);	
					

	  return ResponseEntity.ok().body(updated);
		
	}
	
	
	@CrossOrigin
	@PutMapping(path = "protected/users/atualizar/{id_usuario}")
	public ResponseEntity mudarConfiguracoes(@PathVariable("id_usuario") int id_usuario, @RequestBody User user) {
		return userRepository.findById(id_usuario).map(record -> {

			record.setRg(user.getRg());
			record.setNome(user.getNome());
			record.setSobrenome(user.getSobrenome());

			record.setCargo(user.getCargo());
			record.setUrl_img_perfil(user.getUrl_img_perfil());

			// dados de endereco
			record.setTipo_endereco(user.getTipo_endereco());
			record.setCep(user.getCep());
			record.setLogradouro(user.getLogradouro());
			record.setNumero(user.getNumero());
			record.setBairro(user.getBairro());
			record.setCidade(user.getCidade());
			record.setEstado(user.getEstado());
			record.setComplemento(user.getComplemento());
			record.setSobre(user.getSobre());
			
			User updated = userRepository.save(record);

			return ResponseEntity.ok().body(updated);
		}).orElse(ResponseEntity.notFound().build());
	}
	
	
	@CrossOrigin
	@PutMapping(path = "protected/users/mudarsenha/{id_usuario}")
	public ResponseEntity mudarSenha(@PathVariable("id_usuario") int id_usuario, @RequestBody User user) {
		return userRepository.findById(id_usuario).map(record -> {

			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

			record.setSenha(encoder.encode(user.getSenha()));

			User updated = userRepository.save(record);

			return ResponseEntity.ok().body(updated);
		}).orElse(ResponseEntity.notFound().build());
	}


}
