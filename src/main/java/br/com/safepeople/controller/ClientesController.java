package br.com.safepeople.controller;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import com.google.gson.Gson;


import br.com.safepeople.models.Cliente;



import br.com.safepeople.models.Fornecedor;
import br.com.safepeople.models.Log;
import br.com.safepeople.models.User;

import br.com.safepeople.repository.ClienteRepository;

import br.com.safepeople.repository.LogRepository;
import br.com.safepeople.repository.UserRepository;
import br.com.safepeople.services.ClientesService;




import br.com.safepeople.utils.MessageResponse;


import br.com.safepeople.utils.SignUpClienteRequest;
import br.com.safepeople.utils.SignUpFornecedorRequest;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("v1/")
public class ClientesController {

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	ClientesService service;

	@Autowired
	ClienteRepository clienteRepository;



	@Autowired
	UserRepository userRepository;
	
	@Autowired
	LogRepository logRepository;
	

	@CrossOrigin
	@PutMapping(path = "protected/cliente/mudarsenha/{id_usuario}")
	public ResponseEntity mudarSenha(@PathVariable("id_usuario") int id_usuario, @RequestBody Cliente cliente) {
		return clienteRepository.findById(id_usuario).map(record -> {

			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

			record.setSenhaweb(encoder.encode(cliente.getSenhaweb()));

			Cliente updated = clienteRepository.save(record);

			return ResponseEntity.ok().body(updated);
		}).orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("protected/clientes/listar")
	public Page<Cliente> search(@RequestParam(value = "cpf_cnpj", required = false, defaultValue = "") String cpf_cnpj,
			@RequestParam(value = "page", required = true) int page,
			@RequestParam(value = "size", required = false, defaultValue = "50") int size)

	{
		return service.listAllClientes(page, size, cpf_cnpj);

	}

	

	@GetMapping("protected/clientes/listarpaginadosporfornecedor")
	public Page<Cliente> search(@RequestParam(value = "cpf_cnpj", required = false, defaultValue = "") String cpf_cnpj,
			@RequestParam(value = "page", required = true) int page,
			@RequestParam(value = "size", required = false, defaultValue = "50") int size,
			@RequestParam(value = "idfornecedor", required = true) int idfornecedor)

	{
		return service.listAllClientesPorFornecedor(page, size, cpf_cnpj, idfornecedor);

	}

	@GetMapping("protected/clientes/listarTodos")
	public List<Cliente> searchAll()

	{
		return clienteRepository.findAll();

	}

	@GetMapping("protected/clientes/listarporfornecedor/{id_fornecedor}")
	public List<Cliente> buscarClientesPorFornecedor(@PathVariable int id_fornecedor)

	{
		return clienteRepository.findByFornecedorId(id_fornecedor);

	}

	@PostMapping("protected/clientes/cadastrar")
	public boolean cadastrarCliente(@RequestBody Cliente cliente) {

		System.out.println("Id fornecedor: " + cliente.getFornecedor().getId_fornecedor());

		cliente.setStatus(1);
		cliente.setSenhaweb(encoder.encode(cliente.getSenhaweb()));
		cliente.setData_cadastro(LocalDateTime.now());

		cliente = clienteRepository.saveAndFlush(cliente);

		if (cliente != null) {

			return true;
		} else {
			return false;
		}

	}






	@CrossOrigin
	@GetMapping(path = "protected/retornardadoscliente/{id}")
	public Optional<Cliente> retornarDadosCliente(@PathVariable int id) {

		return clienteRepository.findById(id);
	}

	


	

	
	

	
	@CrossOrigin
	@PutMapping(path = "protected/clientes/atualizar/{id_cliente}")
	public ResponseEntity atualizar(@PathVariable("id_cliente") int id_cliente, @RequestBody Cliente cliente) {
		return clienteRepository.findById(id_cliente).map(record -> {

			if (cliente.getTipo_cliente() == 0) {
				// pessoa fisica

				record.setRg(cliente.getRg());
				record.setNascimento(cliente.getNascimento());
				record.setNome(cliente.getNome());
				record.setSobrenome(cliente.getSobrenome());
				record.setDescricao(cliente.getDescricao());

			} else {
				// pessoa juridica
				record.setRazao_social(cliente.getRazao_social());
				record.setNome_fantasia(cliente.getNome_fantasia());
				record.setPorte(cliente.getPorte());
				record.setAtividade(cliente.getAtividade());
				record.setIe(cliente.getIe());
			}

			// dados de endereco
			record.setRua(cliente.getRua());
			record.setBloco(cliente.getBloco());
			record.setNumero(cliente.getNumero());
			record.setApartamento(cliente.getApartamento());
			record.setComplemento(cliente.getComplemento());

			Cliente updated = clienteRepository.save(record);

			return ResponseEntity.ok().body(updated);
		}).orElse(ResponseEntity.notFound().build());
	}

	


}
