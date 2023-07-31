package br.com.safepeople.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



import br.com.safepeople.models.Receptor;
import br.com.safepeople.models.Transmissor;


import br.com.safepeople.models.Cliente;


import br.com.safepeople.models.Fornecedor;
import br.com.safepeople.repository.FornecedorRepository;
import br.com.safepeople.services.ClientesService;
import br.com.safepeople.services.FornecedoresService;
import br.com.safepeople.utils.MessageResponse;
import br.com.safepeople.utils.SignUpFornecedorRequest;

import br.com.safepeople.repository.ReceptorRepository;
import br.com.safepeople.repository.TransmissorRepository;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping("v1/")
public class FornecedoresController {

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	FornecedoresService service;

	@Autowired
	FornecedorRepository fornecedorRepository;
	
	
	@Autowired
	TransmissorRepository transmissorRepository;



	@Autowired
	ReceptorRepository receptorRepository;


	@GetMapping("protected/fornecedores/listar")
	public Page<Fornecedor> search(
			@RequestParam(value = "cpf_cnpj", required = false, defaultValue = "") String cpf_cnpj,
			@RequestParam(value = "page", required = true) int page,
			@RequestParam(value = "size", required = false, defaultValue = "50") int size)

	{
		return service.listAllClientes(page, size, cpf_cnpj);

	}

	@ApiOperation(value = "Listar Todos os Fornecedores")
	@GetMapping("protected/fornecedores/listarTodos")
	public List<Fornecedor> searchAll() {
		return fornecedorRepository.findAll();
	}

	@ApiOperation(value = "Cadastrar Fornecedor")
	@PostMapping("protected/fornecedores/cadastrar")
	public ResponseEntity cadastrarFornecedor(@Valid @RequestBody SignUpFornecedorRequest signUpRequest) {

		// Create new user's account
		Fornecedor fornecedor = new Fornecedor();

		// 0 pessoa fisica
		// 1 pessoa juridica
		fornecedor.setTipo_fornecedor(signUpRequest.getTipo_fornecedor());

		// pf
		fornecedor.setCpf(signUpRequest.getCpf());
		fornecedor.setRg(signUpRequest.getRg());
		fornecedor.setNascimento(signUpRequest.getNascimento());
		fornecedor.setNome(signUpRequest.getNome());
		fornecedor.setSobrenome(signUpRequest.getSobrenome());

		// pj
		fornecedor.setCnpj(signUpRequest.getCnpj());
		fornecedor.setRazao_social(signUpRequest.getRazao_social());
		fornecedor.setNome_fantasia(signUpRequest.getNome_fantasia());
		fornecedor.setPorte(signUpRequest.getPorte());
		fornecedor.setAtividade(signUpRequest.getAtividade());
		fornecedor.setIe(signUpRequest.getIe());

		// endereco
		fornecedor.setTipo_endereco(signUpRequest.getTipo_endereco());
		fornecedor.setLogradouro(signUpRequest.getLogradouro());
		fornecedor.setNumero(signUpRequest.getNumero());
		fornecedor.setBairro(signUpRequest.getBairro());
		fornecedor.setComplemento(signUpRequest.getComplemento());
		fornecedor.setInfo_adicional(signUpRequest.getInfo_adicional());
		fornecedor.setCep(signUpRequest.getCep());
		fornecedor.setCidade(signUpRequest.getCidade());
		fornecedor.setEstado(signUpRequest.getEstado());

		// ambos
		fornecedor.setDescricao(signUpRequest.getDescricao());
		fornecedor.setEmail(signUpRequest.getEmail());
		fornecedor.setStatus(1);
		fornecedor.setSenhaweb(encoder.encode(signUpRequest.getSenhaweb()));
		fornecedor.setData_cadastro(LocalDateTime.now());

		fornecedor = fornecedorRepository.saveAndFlush(fornecedor);

		if (fornecedor != null) {

		
			return ResponseEntity.ok(new MessageResponse("Fornecedor registered successfully!"));

		} else {
			return ResponseEntity.notFound().build();
		}

	}



	@CrossOrigin
	@GetMapping(path = "protected/retornardadosfornecedor/{id}")
	public Optional<Fornecedor> retornarDadosFornecedor(@PathVariable int id) {

		return fornecedorRepository.findById(id);
	}

	@CrossOrigin
	@PutMapping(path = "protected/fornecedores/atualizar/{id_fornecedor}")
	public ResponseEntity mudarConfiguracoes(@PathVariable("id_fornecedor") int id_fornecedor,
			@RequestBody Fornecedor fornecedor) {
		return fornecedorRepository.findById(id_fornecedor).map(record -> {

			if (fornecedor.getTipo_fornecedor() == 0) {
				// pessoa fisica

				record.setRg(fornecedor.getRg());
				record.setNascimento(fornecedor.getNascimento());
				record.setNome(fornecedor.getNome());
				record.setSobrenome(fornecedor.getSobrenome());
				record.setDescricao(fornecedor.getDescricao());

			} else {
				// pessoa juridica
				record.setRazao_social(fornecedor.getRazao_social());
				record.setNome_fantasia(fornecedor.getNome_fantasia());
				record.setPorte(fornecedor.getPorte());
				record.setAtividade(fornecedor.getAtividade());
				record.setIe(fornecedor.getIe());
			}

			// dados de endereco
			record.setTipo_endereco(fornecedor.getTipo_endereco());
			record.setCep(fornecedor.getCep());
			record.setLogradouro(fornecedor.getLogradouro());
			record.setNumero(fornecedor.getNumero());
			record.setBairro(fornecedor.getBairro());
			record.setCidade(fornecedor.getCidade());
			record.setEstado(fornecedor.getEstado());
			record.setComplemento(fornecedor.getComplemento());
			record.setInfo_adicional(fornecedor.getInfo_adicional());
			record.setLatitude(fornecedor.getLatitude());
			record.setLongitude(fornecedor.getLongitude());

			Fornecedor updated = fornecedorRepository.save(record);

			return ResponseEntity.ok().body(updated);
		}).orElse(ResponseEntity.notFound().build());
	}
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	class DadosMonitoramento {

		Fornecedor fornecedor;
		List<Receptor> receptores;
		List<Transmissor> transmissores;

	}

	
	@GetMapping("protected/fornecedores/listadordadosmonitoramento/{id_fornecedor}")
	public DadosMonitoramento getDadosMonitoramento(@PathVariable int id_fornecedor)

	{
		DadosMonitoramento objetos_monitoramento = new DadosMonitoramento();

		Optional<Fornecedor> fornecedor = fornecedorRepository.findById(id_fornecedor);
		objetos_monitoramento.setFornecedor(fornecedor.get());

		// listar receptores
		List<Receptor> receptores = receptorRepository.listarPorFornecedor(id_fornecedor);

		
	

		objetos_monitoramento.setReceptores(receptores);

		// listar transmissores
		List<Transmissor> transmissores = transmissorRepository.listarAtivosPorFornecedor(id_fornecedor);

		
		
		
		
		

		return objetos_monitoramento;

	}
	

	

}
