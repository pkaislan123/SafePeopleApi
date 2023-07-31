package br.com.safepeople.controller;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.safepeople.models.Transmissor;
import br.com.safepeople.repository.TransmissorRepository;
import br.com.safepeople.repository.TransmissorRepository;

@CrossOrigin
@RestController
@RequestMapping("v1/")
public class TransmissorController {

	@Autowired
	PasswordEncoder encoder;

	
	
	@Autowired
	TransmissorRepository transmissorRepository;

	@CrossOrigin
	@GetMapping("protected/transmissores/listartodas")
	public List<Transmissor> searchAll()

	{
		System.out.println("Chamada feita!");
		List<Transmissor> todos_transmissores = transmissorRepository.findAll();
		for (Transmissor tras : todos_transmissores) {
			// System.out.println(tras.toString());

		}
		return todos_transmissores;

	}

	@CrossOrigin
	@GetMapping("protected/transmissores/listarporfornecedor/{id_fornecedor}")
	public List<Transmissor> listarControladorasPorFornecedor(@PathVariable int id_fornecedor) {
		return transmissorRepository.listarPorFornecedor(id_fornecedor);
	}

	@CrossOrigin
	@GetMapping("protected/transmissores/listarporreceptor/{id_receptor}")
	public List<Transmissor> listarTransmissoresPorRecetpr(@PathVariable int id_receptor) {
		return transmissorRepository.listarPorReceptor(id_receptor);
	}

	@CrossOrigin
	@GetMapping("protected/transmissores/listar/{id}")
	public Optional<Transmissor> buscarPorId(@PathVariable int id)

	{
		Optional<Transmissor> transmissor = transmissorRepository.findById(id);

		if (transmissor.get() != null) {
			try {
				Duration duracao = Duration.between(transmissor.get().getData_hora_ultima_resposta(),
						LocalDateTime.now()); // duração entre a hora inicial e final
				if (duracao.getSeconds() > 45) { // verifica se passaram mais de 45 segundos
					System.out.println("Já passaram 45 segundos!");
					transmissor.get().setStatus_online(0);
				} else {
					System.out.println("Não passaram 45 segundos ainda.");
				}
			} catch (Exception e) {
				System.out.println("Nenhum dado de online recebido!");
				transmissor.get().setStatus_online(0);
			}

		}

		return transmissor;

	}

	@CrossOrigin
	@PostMapping("protected/transmissores/cadastrar")
	public boolean cadastrarControladora(@RequestBody Transmissor Transmissor) {

		return transmissorRepository.save(Transmissor) != null;

	}

	@CrossOrigin
	@DeleteMapping(path = "protected/transmissores/excluir/{id}")
	public ResponseEntity<Void> excluirControladora(@PathVariable int id) {

		transmissorRepository.deleteById(id);
		return ResponseEntity.noContent().build();

	}

	@CrossOrigin
	@PutMapping(path = "protected/transmissores/atualizar/{id}")
	public ResponseEntity atualizarControladora(@PathVariable("id") int id, @RequestBody Transmissor Transmissor) {

		return transmissorRepository.findById(id).map(record -> {

			record.setNumero_serie(Transmissor.getNumero_serie());
			record.setMarca(Transmissor.getMarca());
			record.setModelo(Transmissor.getModelo());
			record.setData_fabricacao(Transmissor.getData_fabricacao());
			record.setStatus(Transmissor.getStatus());
			record.setDescricao(Transmissor.getDescricao());

			record.setFornecedor(Transmissor.getFornecedor());
			record.setReceptor(Transmissor.getReceptor());

			Transmissor updated = transmissorRepository.save(record);
			return ResponseEntity.ok().body(updated);
		}).orElse(ResponseEntity.notFound().build());
	}

	@CrossOrigin
	@GetMapping(path = "protected/transmissores/informarOnline/{numero_serie}/{senha}")
	public String informarOnline(@PathVariable("numero_serie") int numero_serie, @PathVariable("senha") String senha) {

		Transmissor transmissor = null;

		String resposta = "";


				transmissor = transmissorRepository.listarPorNumeroSerie(numero_serie).get();

			
			
			
			
			
			if (transmissor != null) {

				if (encoder.matches(senha, "TIt@niwm2014" )) {

					transmissor.setData_hora_ultima_resposta(LocalDateTime.now());
					transmissor.setStatus_online(1);

					transmissorRepository.save(transmissor);

					resposta = "ok";

				} else {
					// senha invalida
					resposta = ("erro: wrong password");

				}
			} 
			
			
			
			else {
				// nao existe controladora com este id
				resposta = ("erro: no transmissor");

			}



		return resposta;
	}

}
